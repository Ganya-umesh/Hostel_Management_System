package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Complaint;
import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Service.ComplaintService;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.WardenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {
    private final ComplaintService complaintService;
    private final HostellerService hostellerService;
    private final WardenService wardenService;

    public ComplaintController(ComplaintService complaintService, HostellerService hostellerService, WardenService wardenService) {
        this.complaintService = complaintService;
        this.hostellerService = hostellerService;
        this.wardenService = wardenService;
    }

    @GetMapping("/submit")
    public String showSubmitComplaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "complaints/submit_complaint";
    }

    @PostMapping("/submit/{hostellerId}/{wardenId}")
    public String submitComplaint(@ModelAttribute Complaint complaint, @PathVariable Long hostellerId, @PathVariable Long wardenId) {
        // Verify if the hosteller and warden exist
        Hosteller hosteller = hostellerService.getHostellerById(hostellerId);
        Warden warden = wardenService.getWardenById(wardenId);

        if (hosteller == null || warden == null) {
            // Redirect to an error page or handle the case where either the hosteller or warden does not exist
            return "redirect:/error";
        }

        // Save the complaint
//        complaint.setHosteller(hosteller);
//        complaint.setWarden(warden);
        //complaint.setStatus(Complaint.ComplaintStatus.PENDING);
        complaintService.saveComplaint(complaint, hostellerId, wardenId);

        return "redirect:/complaints/submit_complaint";
    }

    @GetMapping("/submit/{hostellerId}/{wardenId}")
    public String showSubmitComplaintForm(@PathVariable Long hostellerId, @PathVariable Long wardenId, Model model) {
        model.addAttribute("complaint", new Complaint());
        model.addAttribute("hostellerId", hostellerId);
        model.addAttribute("wardenId", wardenId);
        return "complaints/submit_complaint";
    }



    @GetMapping("/resolve")
    public String showResolveComplaintsPage(Model model) {
        // Get all pending complaints
        List<Complaint> pendingComplaints = complaintService.getPendingComplaints();
        model.addAttribute("complaints", pendingComplaints);
        return "complaints/resolve_complaints";
    }

    @PutMapping("/resolve/{wardenId}")
    public ResponseEntity<String> resolveComplaint(@PathVariable Long wardenId, @RequestBody Long complaintId) {
        // Resolve the complaint
        complaintService.resolveComplaint(complaintId, wardenId);

        return ResponseEntity.ok("Complaint resolved successfully!");
    }


    @GetMapping("/resolve/{wardenId}")
    public String showResolveComplaintsPage(@PathVariable Long wardenId, Model model) {
        // Get all pending complaints
        List<Complaint> pendingComplaints = complaintService.getPendingComplaints();
        model.addAttribute("complaints", pendingComplaints);
        model.addAttribute("wardenId", wardenId);
        return "complaints/resolve_complaints";
    }

}