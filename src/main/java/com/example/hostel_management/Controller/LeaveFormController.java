package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Service.AuthenticationService;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.LeaveFormService;
import com.example.hostel_management.Service.ParentService;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;


@RestController
@RequestMapping("/api/leaveform")
public class LeaveFormController {

    private final LeaveFormService leaveFormService;
    private final HostellerService hostellerService;
    private final ParentService parentService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(LeaveFormController.class);


    public LeaveFormController(LeaveFormService leaveFormService, HostellerService hostellerService, ParentService parentService, AuthenticationService authenticationService) {
        this.leaveFormService = leaveFormService;
        this.hostellerService = hostellerService;
        this.parentService = parentService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{hostellerId}/{parentId}")
    public ResponseEntity<LeaveForm> saveLeaveForm(
            @PathVariable Long hostellerId,
            @PathVariable Long parentId,
            @RequestBody LeaveForm leaveForm
    ) {

        // Check if the authenticated parent is associated with the specified hosteller
        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);

        LeaveForm savedLeaveForm = leaveFormService.saveLeaveForm(leaveForm, hostellerId, parentId);
        return new ResponseEntity<>(savedLeaveForm, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public LeaveForm getLeaveFormById(@PathVariable Long id) {
        return leaveFormService.getLeaveFormById(id);
    }

    @PutMapping("/{leaveFormId}/stats/")
    public ResponseEntity<LeaveForm> updateLeaveFormStatusByParent(
            @PathVariable Long leaveFormId,
            @RequestBody LeaveFormUpdateRequest request
    ) {


        Long hostellerId = request.getHostellerId();
        Long parentId = request.getParentId();
        String status = request.getStatus();

        logger.info("Received PUT request for leave form id: {}");
        logger.info("Status: {}");
        logger.info("Hosteller Id: {}");
        logger.info("Parent Id: {}");



        // Check if the authenticated parent is associated with the hosteller of the leave form
        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);

        if ("approve".equalsIgnoreCase(status)) {
            LeaveForm approvedLeaveForm = leaveFormService.approveLeaveFormByParent(leaveFormId);
            return ResponseEntity.ok().body(approvedLeaveForm);
        } else if ("reject".equalsIgnoreCase(status)) {
            LeaveForm rejectedLeaveForm = leaveFormService.rejectLeaveFormByParent(leaveFormId);
            return ResponseEntity.ok().body(rejectedLeaveForm);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Define a class to represent the request body
    @Data
    public static class LeaveFormUpdateRequest {
        private Long hostellerId;
        private Long parentId;
        private String status;
    }







}

