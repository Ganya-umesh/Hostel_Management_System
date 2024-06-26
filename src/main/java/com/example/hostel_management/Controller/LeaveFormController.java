package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Service.AuthenticationService;
import com.example.hostel_management.Service.LeaveFormService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaveform")
public class LeaveFormController {

    private final LeaveFormService leaveFormService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(LeaveFormController.class);

    public LeaveFormController(LeaveFormService leaveFormService,AuthenticationService authenticationService) {
        this.leaveFormService = leaveFormService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLeaveForm);
    }

    @GetMapping("/{id}")
    public LeaveForm getLeaveFormById(@PathVariable Long id) {
        return leaveFormService.getLeaveFormById(id);
    }

    @PutMapping("/{leaveFormId}/stats/parent")
    public ResponseEntity<LeaveForm> updateLeaveFormStatusByParent(
            @PathVariable Long leaveFormId,
            @RequestBody LeaveFormUpdateRequest request
    ) {
        Long hostellerId = request.getHostellerId();
        Long parentId = request.getParentId();
        String status = request.getStatus();

        logger.info("Received PUT request for leave form id: {}", leaveFormId);
        logger.info("Status: {}", status);
        logger.info("Hosteller Id: {}", hostellerId);
        logger.info("Parent Id: {}", parentId);

        // Check if the authenticated parent is associated with the hosteller of the leave form
        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);

        LeaveForm leaveForm = leaveFormService.getLeaveFormById(leaveFormId);

        if (leaveForm == null) {
            return ResponseEntity.notFound().build(); // Return 404 if leave form not found
        }
        if (!leaveForm.getHosteller().getId().equals(hostellerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Return 403 Forbidden if the leave form doesn't belong to the specified hosteller
        }

        LeaveForm updatedLeaveForm;
        if ("approve".equalsIgnoreCase(status)) {
            updatedLeaveForm = leaveFormService.approveLeaveFormByParent(leaveFormId);
        } else if ("reject".equalsIgnoreCase(status)) {
            updatedLeaveForm = leaveFormService.rejectLeaveFormByParent(leaveFormId);
        } else {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if the status is invalid
        }

        return ResponseEntity.ok(updatedLeaveForm);
    }
    @PutMapping("/{leaveFormId}/stats/warden")
    public ResponseEntity<LeaveForm> updateLeaveFormStatusByWarden(
            @PathVariable Long leaveFormId,
            @RequestBody LeaveFormUpdateRequest request
    ) {
        Long wardenId = request.getWardenId();
        String status = request.getStatus();

        logger.info("Received PUT request for leave form id: {}", leaveFormId);
        logger.info("Status: {}", status);
        logger.info("Warden Id: {}", wardenId);

        // Perform authentication and authorization checks here if needed

        if ("approve".equalsIgnoreCase(status)) {
            LeaveForm approvedLeaveForm = leaveFormService.approveLeaveFormByWarden(leaveFormId, wardenId);
            return ResponseEntity.ok().body(approvedLeaveForm);
        } else if ("reject".equalsIgnoreCase(status)) {
            LeaveForm rejectedLeaveForm = leaveFormService.rejectLeaveFormByWarden(leaveFormId, wardenId);
            return ResponseEntity.ok().body(rejectedLeaveForm);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Define a class to represent the request body for updating leave form status
    @Data
    public static class LeaveFormUpdateRequest {
        private Long hostellerId;
        private Long parentId;
        private Long wardenId;
        private String status;
    }


    @PutMapping("/{leaveFormId}/checkin")
    public ResponseEntity<LeaveForm> updateCheckInDateTimeByHosteller(
            @PathVariable Long leaveFormId,
            @RequestBody UpdateCheckInRequest request
    ) {
        Long hostellerId = request.getHostellerId();

        logger.info("Received PUT request for leave form id: {}", leaveFormId);
        logger.info("Hosteller Id: {}", hostellerId);

        LeaveForm updatedLeaveForm = leaveFormService.updateCheckInDateTimeByHosteller(leaveFormId, hostellerId);
        return ResponseEntity.ok().body(updatedLeaveForm);
    }

    // Define a class to represent the request body for updating check-in date and time
    @Data
    public static class UpdateCheckInRequest {
        private Long hostellerId;
    }

    @PutMapping("/{leaveFormId}/checkin/approve")
    public ResponseEntity<LeaveForm> approveCheckInRequestByWarden(
            @PathVariable Long leaveFormId,
            @RequestBody WardenActionRequest request
    ) {
        Long wardenId = request.getWardenId();

        logger.info("Received PUT request to approve check-in for leave form id: {}", leaveFormId);
        logger.info("Warden Id: {}", wardenId);

        LeaveForm approvedLeaveForm = leaveFormService.approveCheckInRequestByWarden(leaveFormId, wardenId);
        return ResponseEntity.ok().body(approvedLeaveForm);
    }

    // this is an alternative of using map function
    @Data
    public static class WardenActionRequest {
        private Long wardenId;
    }

}

