package com.example.hostel_management.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "complaint")
    private String complaint_description;

    @Column(name = "hosteller_id")
    private Long hostellerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", columnDefinition = "ENUM('Pending', 'Resolved')")
    public ComplaintStatus status;

    @Column(name = "lodging_time")
    private LocalDateTime lodgingTime;

    @Column(name = "resolving_time")
    private LocalDateTime resolvingTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_type", columnDefinition = "ENUM('Electricity','Plumbing', 'Water', 'Food', 'Noise','Internet and Wifi', 'Others')")
    private ComplaintType complaintType;

    public enum ComplaintStatus {
        PENDING, RESOLVED
    }

    public enum ComplaintType {
        ELECTRICITY, PLUMBING, WATER, FOOD, NOISE, INTERNET_AND_WIFI, OTHERS
    }



}
