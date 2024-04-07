package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="hosteller")
public class Hosteller{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;


    @Column(name = "phone_number", unique = true,nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_grad_year")
    public GradYear currentGradYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "branch")
    public Branch branch;

    public enum GradYear {
        YEAR_1, YEAR_2, YEAR_3, YEAR_4
    }

    public enum Branch {
        CSE, ECE, MECH, AI_ML, EEE, BT
    }

    @Override
    public String toString() {
        return "Hosteller{" +
                "username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roomNumber=" + roomNumber +
                ", currentGradYear='" + currentGradYear + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "hosteller",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "hosteller-complaints")
    private List<Complaint> complaintsLodged;

    @OneToMany(mappedBy = "hosteller",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference(value = "hosteller-leaveforms")
    private List<LeaveForm> leavesApplied;

    @OneToMany(mappedBy = "hosteller",fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference(value = "hosteller-payments")
    private List<Payment> paymentsMade; //we don't want cascade.remove here because we don't want to remove the payment if the hosteller is removed

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "hosteller", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "hosteller-parent")
    private Parent parent;



}
