package com.example.hostel_management.Service;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Repository.LeaveFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Service
public class LeaveFormService {

    private final LeaveFormRepository leaveFormRepository;
    private final HostellerService hostellerService;
    private final ParentService parentService;
    private final WardenService wardenService;

    @Autowired
    public LeaveFormService(LeaveFormRepository leaveFormRepository, HostellerService hostellerService, ParentService parentService, WardenService wardenService) {
        this.leaveFormRepository = leaveFormRepository;
        this.hostellerService = hostellerService;
        this.parentService = parentService;
        this.wardenService = wardenService;
    }

    public LeaveForm saveLeaveForm(LeaveForm leaveForm, Long hostellerId, Long parentId) {
        // Set hosteller and parent for the leave form
        leaveForm.setHosteller(hostellerService.getHostellerById(hostellerId));
        leaveForm.setParent(parentService.getParentById(parentId));

        return leaveFormRepository.save(leaveForm);
    }

    public LeaveForm getLeaveFormById(Long id) {
        return leaveFormRepository.findById(id).orElse(null);
    }

    public Long getHostellerIdByLeaveFormId(Long leaveFormId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));
        return leaveForm.getHosteller().getId();
    }


    public LeaveForm approveLeaveFormByParent(Long leaveFormId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));

        // Update form status to PARENT_APPROVED
        leaveForm.setFormStatus(LeaveForm.FormStatus.PARENT_APPROVED);

        return leaveFormRepository.save(leaveForm);
    }

    public LeaveForm rejectLeaveFormByParent(Long leaveFormId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));

        // Update form status to REJECTED
        leaveForm.setFormStatus(LeaveForm.FormStatus.REJECTED);

        return leaveFormRepository.save(leaveForm);
    }

    public LeaveForm approveLeaveFormByWarden(Long leaveFormId, Long wardenId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));

        // Check if the leave form has been approved by the parent
        if (leaveForm.getFormStatus() != LeaveForm.FormStatus.PARENT_APPROVED) {
            throw new IllegalStateException("Leave form can only be approved by warden after it has been approved by parent.");
        }

        // Check if the provided wardenId exists
        if (wardenService.getWardenById(wardenId) == null) {
            throw new IllegalArgumentException("Warden with ID " + wardenId + " does not exist.");
        }

        // Update form status to APPROVED_BY_WARDEN
        leaveForm.setFormStatus(LeaveForm.FormStatus.WARDEN_APPROVED);
        leaveForm.setWarden(wardenService.getWardenById(wardenId)); // Set the warden

        return leaveFormRepository.save(leaveForm);
    }

    public LeaveForm rejectLeaveFormByWarden(Long leaveFormId, Long wardenId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));

        // Check if the leave form has been approved by the parent
        if (leaveForm.getFormStatus() != LeaveForm.FormStatus.PARENT_APPROVED) {
            throw new IllegalStateException("Leave form can only be rejected by warden after it has been approved by parent.");
        }

        // Check if the provided wardenId exists
        if (wardenService.getWardenById(wardenId) == null) {
            throw new IllegalArgumentException("Warden with ID " + wardenId + " does not exist.");
        }

        // Update form status to REJECTED_BY_WARDEN
        leaveForm.setFormStatus(LeaveForm.FormStatus.REJECTED);
        leaveForm.setWarden(wardenService.getWardenById(wardenId)); // Set the warden

        return leaveFormRepository.save(leaveForm);
    }

    public LeaveForm updateCheckInDateTimeByHosteller(Long leaveFormId, Long hostellerId) {
        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));

        // Check if the leave form status is WARDEN_APPROVED
        if (leaveForm.getFormStatus() != LeaveForm.FormStatus.WARDEN_APPROVED) {
            throw new IllegalStateException("Check-in date can only be updated if the leave form is approved by the warden.");
        }

        // Check if the hosteller associated with the leave form is the one making the update
        if (!leaveForm.getHosteller().getId().equals(hostellerId)) {
            throw new IllegalArgumentException("Only the hosteller associated with this leave form can update it.");
        }

        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(7, 0)) || currentTime.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalStateException("Check-in requests are only allowed between 7 AM and 9 PM.");
        }

        // Update check-in date and time to current date and time
        leaveForm.setCheckInDate(LocalDate.now());
        leaveForm.setCheckInTime(LocalDateTime.now());

        // Check if the provided check-in date is later than the checkout date
        if (leaveForm.getCheckInDate().isBefore(leaveForm.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be later than checkout date.");
        }

        leaveForm.setFormStatus(LeaveForm.FormStatus.CHECKIN_REQUESTED);

        return leaveFormRepository.save(leaveForm);
    }

}
