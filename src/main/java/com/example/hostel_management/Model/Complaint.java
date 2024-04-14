//package com.example.hostel_management.Model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "complaint")
//public class Complaint {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "complaint_description", nullable = false)
//    private String complaint_description;
//
////    @Column(name = "hostellerId")
////    private Long hostellerId;
////
////    @Column(name = "resolved_by_warden_id")
////    private Long wardenId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "Status")
//    public ComplaintStatus status;
//
//    @Column(name = "lodging_time",nullable = false)
//    private LocalDateTime lodgingTime;
//
//    @Column(name = "resolving_time")
//    private LocalDateTime resolvingTime;
//
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "complaint_type")
//    private ComplaintType complaintType;
//
//    public enum ComplaintStatus {
//        PENDING, RESOLVED
//    }
//
//    public enum ComplaintType {
//        ELECTRICITY, PLUMBING, WATER, FOOD, NOISE, INTERNET_AND_WIFI, OTHERS
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "hosteller_id",nullable = false)
//    @JsonBackReference(value = "hosteller-complaints")
//    private Hosteller hosteller;
//
//    @ManyToOne
//    @JoinColumn(name = "warden_id",nullable = false)
//    @JsonBackReference(value = "warden-complaints")
//    private Warden warden;
//
//}
