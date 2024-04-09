package com.example.hostel_management.Service;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Repository.LeaveFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class LeaveFormService {

    private final LeaveFormRepository leaveFormRepository;
    private final HostellerService hostellerService;
    private final ParentService parentService;

    @Autowired
    public LeaveFormService(LeaveFormRepository leaveFormRepository, HostellerService hostellerService, ParentService parentService) {
        this.leaveFormRepository = leaveFormRepository;
        this.hostellerService = hostellerService;
        this.parentService = parentService;
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
}
