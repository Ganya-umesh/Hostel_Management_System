package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Service.AuthenticationService;
import com.example.hostel_management.Service.LeaveFormService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leaveform")
public class LeaveFormController {

    private final LeaveFormService leaveFormService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(LeaveFormController.class);

    public LeaveFormController(LeaveFormService leaveFormService,AuthenticationService authenticationService) {
        this.leaveFormService = leaveFormService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/submit_lf/{hostellerId}/{parentId}")
    public String submitLeaveForm(@ModelAttribute LeaveForm leaveform, @PathVariable Long hostellerId, @PathVariable Long parentId, Model model) {
        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);

        LeaveForm savedLeaveForm = leaveFormService.saveLeaveForm(leaveform, hostellerId, parentId);
        return "redirect:/leaveform/submit_leaveform";
    }

    @GetMapping("/submit_lf/{hostellerId}/{parentId}")
    public String showSubmitLeaveFormPage(@PathVariable Long hostellerId, @PathVariable Long parentId, Model model) {

        model.addAttribute("leaveForm", new LeaveForm());
        model.addAttribute("hostellerId", hostellerId);
        model.addAttribute("parentId", parentId);
        return "leaveform/submit_leaveform";
    }

    @PutMapping("/update_lf/{leaveFormId}/stats/parent/{parentId}")
    public String updateLeaveFormStatusByParent(
            @PathVariable Long leaveFormId,
            @PathVariable Long parentId,
            @ModelAttribute("leaveForm") LeaveForm leaveForm) {

        // Check if the authenticated parent is associated with the hosteller of the leave form
        Long hostellerId = leaveForm.getHosteller().getId();
        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);

        LeaveForm.FormStatus formStatus = leaveForm.getFormStatus();

        if (formStatus == LeaveForm.FormStatus.PARENT_APPROVED) {
            leaveFormService.approveLeaveFormByParent(leaveFormId);
        } else if (formStatus == LeaveForm.FormStatus.REJECTED) {
            leaveFormService.rejectLeaveFormByParent(leaveFormId);
        } else {
            // Handle the case when the status is invalid
            throw new IllegalArgumentException("Invalid status: " + formStatus);
        }

        // Update the leave form in the database or perform any other necessary operations

        return "leaveform/parent_update_status";
    }

    @GetMapping("/update_lf/{leaveFormId}/stats/parent/{parentId}")
    public String showUpdateLeaveFormStatusPage(@PathVariable Long leaveFormId, @PathVariable Long parentId, Model model) {
        LeaveForm leaveForm = leaveFormService.getLeaveFormById(leaveFormId);
        if (leaveForm == null) {
            // Handle the case when the leave form is not found
            return "error";
        }

        // Check if the authenticated parent is associated with the hosteller of the leave form
        //authenticationService.validateParentHostellerRelationship(parentId, leaveForm.getHosteller().getId());

        model.addAttribute("leaveForm", leaveForm);
        model.addAttribute("parentId", parentId);
        return "leaveform/parent_update_status";
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

    @GetMapping("/{id}")
    public LeaveForm getLeaveFormById(@PathVariable Long id) {
        return leaveFormService.getLeaveFormById(id);
    }
}

