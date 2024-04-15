package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.LeaveForm;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.LeaveFormService;
import com.example.hostel_management.Service.UserService;
import com.example.hostel_management.Service.WardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;
import com.example.hostel_management.Model.LeaveForm.LeaveStatus;



@Controller
@RequestMapping("/api/leaveform")
public class LeaveFormController {
    private final LeaveFormService leaveFormService;
    private final HttpSession httpSession;

    @Autowired
    public LeaveFormController(HttpSession httpSession, LeaveFormService leaveFormService) {
        this.leaveFormService = leaveFormService;
        this.httpSession = httpSession;
    }

    @GetMapping("/form")
    public String showDashboard(@AuthenticationPrincipal Model model) {
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        return "leaveForm";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute LeaveForm leaveForm, @RequestParam("start_date") String startDateStr, @RequestParam("end_date") String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            leaveForm.setStartDate(startDate);
            leaveForm.setEndDate(endDate);

            System.out.println("leave form details " + leaveForm);

            leaveFormService.saveLeaveForm(leaveForm);
            return "redirect:/hosteller/dashboard";
        } catch (Exception e) {
            System.err.println("Error occurred while registering: " + e.getMessage());
            return "error";
        }
    }
    @GetMapping("/approve")
    public String approveLeaveForms(Model model) {
        // Query the leave_form table to get all leave forms
        List<LeaveForm> leaveForms = leaveFormService.getAllLeaveForms();

        // Add the leave forms to the model to be displayed in the view
        model.addAttribute("leaveForms", leaveForms);

        // Return the view to display the leave forms
        return "approveLeaveForms";
    }

    @PostMapping("/approveAction")
    public String approveAction(@RequestParam("id") Long id, @RequestParam("status") String statusStr) {
        try {
            LeaveForm leaveForm = leaveFormService.getLeaveFormById(id);
            if (leaveForm == null) {
                // Handle case where leave form with the given id is not found
                return "redirect:/errorrr";
            }

            // Convert status string to enum value
            LeaveForm.LeaveStatus status = LeaveForm.LeaveStatus.valueOf(statusStr);

            // Update leave form status
            leaveForm.setStatus(status);

            // Save the updated leave form
            leaveFormService.saveLeaveForm(leaveForm);

            // Redirect to the approval page or another appropriate page
            return "redirect:/api/leaveform/approve";
        } catch (IllegalArgumentException e) {
            // Handle case where invalid status string is provided
            return "redirect:/errorss";
        } catch (Exception e) {
            // Handle other exceptions
            return "redirect:/error";
        }
    }
    @GetMapping("/checkStatus")
    public String checkStatus(Model model) {
        // Retrieve the username of the logged-in user
        String username = (String) httpSession.getAttribute("username");

        // Query the leave_form table to get the leave forms submitted by the user
        List<LeaveForm> userLeaveForms = leaveFormService.getLeaveFormsByHosteller(username);

        System.out.println("the leave form is "+ userLeaveForms);

        // Add the leave forms to the model to be displayed in the view
        model.addAttribute("userLeaveForms", userLeaveForms);

        // Return the view to display the leave form statuses
        return "checkLeaveFormStatus";
    }

}
//
//    private final LeaveFormService leaveFormService;
//    private final AuthenticationService authenticationService;
//    private static final Logger logger = LoggerFactory.getLogger(LeaveFormController.class);
//
//    public LeaveFormController(LeaveFormService leaveFormService,AuthenticationService authenticationService) {
//        this.leaveFormService = leaveFormService;
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/{hostellerId}/{parentId}")
//    public ResponseEntity<LeaveForm> saveLeaveForm(
//            @PathVariable Long hostellerId,
//            @PathVariable Long parentId,
//            @RequestBody LeaveForm leaveForm
//    ) {
//        // Check if the authenticated parent is associated with the specified hosteller
//        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);
//
//        LeaveForm savedLeaveForm = leaveFormService.saveLeaveForm(leaveForm, hostellerId, parentId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedLeaveForm);
//    }
//
//    @GetMapping("/{id}")
//    public LeaveForm getLeaveFormById(@PathVariable Long id) {
//        return leaveFormService.getLeaveFormById(id);
//    }
//
//    @PutMapping("/{leaveFormId}/stats/parent")
//    public ResponseEntity<LeaveForm> updateLeaveFormStatusByParent(
//            @PathVariable Long leaveFormId,
//            @RequestBody LeaveFormUpdateRequest request
//    ) {
//        Long hostellerId = request.getHostellerId();
//        Long parentId = request.getParentId();
//        String status = request.getStatus();
//
//        logger.info("Received PUT request for leave form id: {}", leaveFormId);
//        logger.info("Status: {}", status);
//        logger.info("Hosteller Id: {}", hostellerId);
//        logger.info("Parent Id: {}", parentId);
//
//        // Check if the authenticated parent is associated with the hosteller of the leave form
//        authenticationService.validateParentHostellerRelationship(parentId, hostellerId);
//
//        LeaveForm leaveForm = leaveFormService.getLeaveFormById(leaveFormId);
//
//        if (leaveForm == null) {
//            return ResponseEntity.notFound().build(); // Return 404 if leave form not found
//        }
//        if (!leaveForm.getHosteller().getId().equals(hostellerId)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(null); // Return 403 Forbidden if the leave form doesn't belong to the specified hosteller
//        }
//
//        LeaveForm updatedLeaveForm;
//        if ("approve".equalsIgnoreCase(status)) {
//            updatedLeaveForm = leaveFormService.approveLeaveFormByParent(leaveFormId);
//        } else if ("reject".equalsIgnoreCase(status)) {
//            updatedLeaveForm = leaveFormService.rejectLeaveFormByParent(leaveFormId);
//        } else {
//            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if the status is invalid
//        }
//
//        return ResponseEntity.ok(updatedLeaveForm);
//    }
//    @PutMapping("/{leaveFormId}/stats/warden")
//    public ResponseEntity<LeaveForm> updateLeaveFormStatusByWarden(
//            @PathVariable Long leaveFormId,
//            @RequestBody LeaveFormUpdateRequest request
//    ) {
//        Long wardenId = request.getWardenId();
//        String status = request.getStatus();
//
//        logger.info("Received PUT request for leave form id: {}", leaveFormId);
//        logger.info("Status: {}", status);
//        logger.info("Warden Id: {}", wardenId);
//
//        // Perform authentication and authorization checks here if needed
//
//        if ("approve".equalsIgnoreCase(status)) {
//            LeaveForm approvedLeaveForm = leaveFormService.approveLeaveFormByWarden(leaveFormId, wardenId);
//            return ResponseEntity.ok().body(approvedLeaveForm);
//        } else if ("reject".equalsIgnoreCase(status)) {
//            LeaveForm rejectedLeaveForm = leaveFormService.rejectLeaveFormByWarden(leaveFormId, wardenId);
//            return ResponseEntity.ok().body(rejectedLeaveForm);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // Define a class to represent the request body for updating leave form status
//    @Data
//    public static class LeaveFormUpdateRequest {
//        private Long hostellerId;
//        private Long parentId;
//        private Long wardenId;
//        private String status;
//    }
//
//
//    @PutMapping("/{leaveFormId}/checkin")
//    public ResponseEntity<LeaveForm> updateCheckInDateTimeByHosteller(
//            @PathVariable Long leaveFormId,
//            @RequestBody UpdateCheckInRequest request
//    ) {
//        Long hostellerId = request.getHostellerId();
//
//        logger.info("Received PUT request for leave form id: {}", leaveFormId);
//        logger.info("Hosteller Id: {}", hostellerId);
//
//        LeaveForm updatedLeaveForm = leaveFormService.updateCheckInDateTimeByHosteller(leaveFormId, hostellerId);
//        return ResponseEntity.ok().body(updatedLeaveForm);
//    }
//
//    // Define a class to represent the request body for updating check-in date and time
//    @Data
//    public static class UpdateCheckInRequest {
//        private Long hostellerId;
//    }
//
//    @PutMapping("/{leaveFormId}/checkin/approve")
//    public ResponseEntity<LeaveForm> approveCheckInRequestByWarden(
//            @PathVariable Long leaveFormId,
//            @RequestBody WardenActionRequest request
//    ) {
//        Long wardenId = request.getWardenId();
//
//        logger.info("Received PUT request to approve check-in for leave form id: {}", leaveFormId);
//        logger.info("Warden Id: {}", wardenId);
//
//        LeaveForm approvedLeaveForm = leaveFormService.approveCheckInRequestByWarden(leaveFormId, wardenId);
//        return ResponseEntity.ok().body(approvedLeaveForm);
//    }
//
//    // this is an alternative of using map function
//    @Data
//    public static class WardenActionRequest {
//        private Long wardenId;
//    }
//
//}
//


