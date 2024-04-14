//package com.example.hostel_management.Controller;
//
//import com.example.hostel_management.Model.Complaint;
//import com.example.hostel_management.Service.ComplaintService;
//import com.example.hostel_management.Service.HostellerService;
//import lombok.Data;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//    @RequestMapping("/api/complaints")
//    public class ComplaintController {
//
//        private final ComplaintService complaintService;
//        private final HostellerService hostellerService;
//        private final Logger logger = LoggerFactory.getLogger(ComplaintController.class);
//
//        public ComplaintController(ComplaintService complaintService, HostellerService hostellerService) {
//            this.complaintService = complaintService;
//            this.hostellerService = hostellerService;
//        }
//
//        @GetMapping("/{id}")
//        public Complaint getComplaintById(@PathVariable Long id) {
//            return complaintService.getComplaintById(id);
//        }
//
//        @PostMapping("/save")
//        public ResponseEntity<Complaint> saveComplaint(@RequestBody SaveComplaintRequest request) {
//            logger.info("Received save complaint request. Payload: {}", request);
//
//            try {
//                Long hostellerId = request.getHostellerId();
//                Long wardenId = request.getWardenId();
//
//                // Save the complaint with the provided hostellerId and wardenId
//                Complaint savedComplaint = complaintService.saveComplaint(request.getComplaint(), hostellerId, wardenId);
//                logger.info("Complaint saved successfully: {}", savedComplaint);
//                return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED);
//            } catch (Exception e) {
//                logger.error("Error saving complaint: {}", e.getMessage());
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//
//        // Define a class to represent the request body for saving a complaint
//        @Data
//        public static class SaveComplaintRequest {
//            private Complaint complaint;
//            private Long hostellerId;
//            private Long wardenId;
//
//
//        }
//    @PutMapping("/resolve/{complaintId}/warden/{wardenId}")
//    public ResponseEntity<Complaint> resolveComplaint(@PathVariable Long complaintId, @PathVariable Long wardenId) {
//        Complaint resolvedComplaint = complaintService.resolveComplaint(complaintId, wardenId);
//        if (resolvedComplaint != null) {
//            return new ResponseEntity<>(resolvedComplaint, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//}
//
