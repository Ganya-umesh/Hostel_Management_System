package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.LeaveFormService;
import com.example.hostel_management.Service.ParentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaveform")
public class LeaveFormController {
    private final LeaveFormService leaveFormService;
    private final HostellerService hostellerService;
    private final ParentService parentService;

    public LeaveFormController(LeaveFormService leaveFormService, HostellerService hostellerService, ParentService parentService) {
        this.leaveFormService = leaveFormService;
        this.hostellerService = hostellerService;
        this.parentService = parentService;
    }
    @PostMapping("/{hostellerId}/{parentId}")
    public ResponseEntity<LeaveForm> saveLeaveForm(
            @PathVariable Long hostellerId,
            @PathVariable Long parentId,
            @RequestBody LeaveForm leaveForm
    ) {
        LeaveForm savedLeaveForm = leaveFormService.saveLeaveForm(leaveForm, hostellerId, parentId);
        return new ResponseEntity<>(savedLeaveForm, HttpStatus.CREATED);
    }


}
