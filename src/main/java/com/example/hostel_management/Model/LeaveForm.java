package com.example.hostel_management.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "LeaveForm")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

//    @Column(name = "requestedBy",nullable = false)
//    private Long hostellerId;

//    @Column(name = "approvedBy",nullable = false)
//    private Long wardenId;

//    @Column(name = "parentID",nullable = false)
//    private Long parentId;

    @Column(name = "checkout_date",nullable = false)
    private LocalDate CheckOutDate;

    @Column(name = "check_out_time",nullable = false)
    private LocalDateTime checkOutTime;



    @Column(name = "expected_arrival_date",nullable = false)
    private LocalDate expectedArrivalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_status", columnDefinition = "ENUM('Pending', 'ParentApproved', 'WardenApproved')")
    private FormStatus formStatus;

    @Column(name = "check_in_date",nullable = false)
    private LocalDate checkInDate;
    @Column(name = "check_in_time",nullable = false)
    private LocalDateTime checkInTime;



    public enum FormStatus {
        PENDING, PARENT_APPROVED, WARDEN_APPROVED
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hosteller_id", insertable = false, updatable = false, nullable = false)
    //UPDATABLE AND INSERTABLE IS FALSE BECAUSE WE ARE NOT ALLOWING TO UPDATE OR INSERT THE HOSTELLER ID
    @JsonBackReference(value = "hosteller-leaveforms")
    private Hosteller hosteller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warden_id", insertable = false, updatable = false, nullable = false)
    @JsonBackReference(value = "warden-leaveforms")
    private Warden warden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",insertable = false, updatable = false, nullable = false)
    @JsonBackReference(value = "parent-leaveforms")
    private Parent parent;
}

