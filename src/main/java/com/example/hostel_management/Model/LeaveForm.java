package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "checkout_date", nullable = false)
    private LocalDate checkOutDate;


    @Column(name = "check_out_time", nullable = false)
    private LocalDateTime checkOutTime;

    @Column(name = "expected_arrival_date", nullable = false)
    private LocalDate expectedArrivalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_status", columnDefinition = "ENUM('PENDING', 'PARENT_APPROVED', 'WARDEN_APPROVED')")
    private FormStatus formStatus;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;


    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    public enum FormStatus {
        PENDING, PARENT_APPROVED, WARDEN_APPROVED
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hosteller_id",nullable = false)
    @JsonBackReference(value = "hosteller-leaveforms")
    private Hosteller hosteller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warden_id")
    @JsonBackReference(value = "warden-leaveforms")
    private Warden warden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",nullable = false)
    @JsonBackReference(value = "parent-leaveforms")
    private Parent parent;
}
