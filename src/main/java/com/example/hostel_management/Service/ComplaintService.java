package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Complaint;
import com.example.hostel_management.Repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final HostellerService hostellerService;
    private final WardenService wardenService;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, HostellerService hostellerService, WardenService wardenService) {
        this.complaintRepository = complaintRepository;
        this.hostellerService = hostellerService;
        this.wardenService = wardenService;
    }
    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }
    public Complaint saveComplaint(Complaint complaint, Long hostellerId, Long wardenId) {

        complaint.setHosteller(hostellerService.getHostellerById(hostellerId));
        complaint.setWarden(wardenService.getWardenById(wardenId));


        complaint.setLodgingTime(LocalDateTime.now());


        complaint.setStatus(Complaint.ComplaintStatus.PENDING);


        return complaintRepository.save(complaint);
    }

    public Complaint resolveComplaint(Long complaintId, Long wardenId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint != null) {
            complaint.setResolvingTime(LocalDateTime.now());
            complaint.setStatus(Complaint.ComplaintStatus.RESOLVED);
            complaint.setWarden(wardenService.getWardenById(wardenId));
            return complaintRepository.save(complaint);
        }
        return null;
    }



}
