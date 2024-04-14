//package com.example.hostel_management.Service;
//
//import com.example.hostel_management.Model.Hosteller;
//import com.example.hostel_management.Model.Parent;
//import com.example.hostel_management.Repository.HostellerRepository;
//import com.example.hostel_management.Repository.ParentRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//@Service
//public class AuthenticationService {
//
//    private final ParentRepository parentRepository;
//    private final HostellerRepository hostellerRepository;
//
//    public AuthenticationService(ParentRepository parentRepository, HostellerRepository hostellerRepository) {
//        this.parentRepository = parentRepository;
//        this.hostellerRepository = hostellerRepository;
//    }
//
//    // other dependencies and constructor
//
//    public void validateParentHostellerRelationship(Long parentId, Long hostellerId) {
//        Parent parent = parentRepository.findById(parentId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent not found with ID: " + parentId));
//        Hosteller hosteller = hostellerRepository.findById(hostellerId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hosteller not found with ID: " + hostellerId));
//
//        // Check if the parent is associated with the hosteller
//        if (!parent.getHosteller().getId().equals(hostellerId)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Parent is not associated with the specified hosteller.");
//        }
//    }
//}
//
//
