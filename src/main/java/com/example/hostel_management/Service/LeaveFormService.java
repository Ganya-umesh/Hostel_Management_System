package com.example.hostel_management.Service;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Repository.LeaveFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

}
