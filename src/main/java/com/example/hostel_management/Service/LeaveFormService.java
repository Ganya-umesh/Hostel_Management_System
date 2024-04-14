package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Repository.HostellerRepository;
import com.example.hostel_management.Repository.LeaveFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;



@Service
public class LeaveFormService {

    private final LeaveFormRepository leaveFormRepository;
    private final HostellerService hostellerService;
    private final ParentService parentService;
    private final WardenService wardenService;
    private final HostellerRepository hostellerRepository;

    @Autowired
    public LeaveFormService(LeaveFormRepository leaveFormRepository, HostellerService hostellerService, ParentService parentService, WardenService wardenService, HostellerRepository hostellerRepository) {
        this.leaveFormRepository = leaveFormRepository;
        this.hostellerService = hostellerService;
        this.parentService = parentService;
        this.wardenService = wardenService;
        this.hostellerRepository = hostellerRepository;
    }

    public LeaveForm saveLeaveForm(LeaveForm leaveForm, Long hostellerId, Long parentId) {
        Optional<Hosteller> hostellerOptional = hostellerRepository.findById(hostellerId);
        if (hostellerOptional.isEmpty()) {
            throw new IllegalArgumentException("Hosteller with ID " + hostellerId + " not found.");
        }

        Hosteller hosteller = hostellerOptional.get();

        //Check if all previous leave forms for the hosteller are in "check-in approved" state
        List<LeaveForm> previousLeaveForms = hosteller.getLeavesApplied();
        for (LeaveForm previousLeaveForm : previousLeaveForms) {
            if (previousLeaveForm.getFormStatus() != LeaveForm.FormStatus.WARDEN_APPROVED) {
                throw new IllegalStateException("Cannot submit a new leave form until all previous leave forms are in 'check-in approved' state.");
            }
        }

        leaveForm.setHosteller(hosteller);
        leaveForm.setParent(parentService.getParentById(parentId));
        leaveForm.setFormStatus(LeaveForm.FormStatus.PENDING);

        return leaveFormRepository.save(leaveForm);
    }


    public LeaveForm getLeaveFormById(Long id) {
        return leaveFormRepository.findById(id).orElse(null);
    }

//    public Long getHostellerIdByLeaveFormId(Long leaveFormId) {
//        LeaveForm leaveForm = leaveFormRepository.findById(leaveFormId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave form not found with ID: " + leaveFormId));
//        return leaveForm.getHosteller().getId();
//    }


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


        if (leaveForm.getFormStatus() != LeaveForm.FormStatus.PARENT_APPROVED) {
            throw new IllegalStateException("Leave form can only be rejected by warden after it has been approved by parent.");
        }


        if (wardenService.getWardenById(wardenId) == null) {
            throw new IllegalArgumentException("Warden with ID " + wardenId + " does not exist.");
        }


        leaveForm.setFormStatus(LeaveForm.FormStatus.REJECTED);
        leaveForm.setWarden(wardenService.getWardenById(wardenId)); // Set the warden

        return leaveFormRepository.save(leaveForm);
    }






}
