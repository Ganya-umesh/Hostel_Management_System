package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "warden")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Warden{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warden_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private Integer PhoneNumber;

     @OneToMany(mappedBy = "warden",fetch=FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Complaint.class)
     @JsonManagedReference(value = "warden-complaints")
     private List<Complaint> complaintsResolved;

     @OneToMany(mappedBy = "warden",fetch=FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = LeaveForm.class)
     @JsonManagedReference(value = "warden-leaveforms")
     private List<LeaveForm> leaveFormsApproved;

     @OneToMany(mappedBy = "warden",fetch=FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Payments.class)
     @JsonManagedReference(value = "warden-payments")
     private List<Payments> paymentsApproved;
}