package com.example.hostel_management.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "LeaveForm")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formId;

    @Column(name = "hosteller_id",nullable = false)
    private Long hostellerId;

    @Column(name = "leave_date",nullable = false)
    private LocalDate leaveDate;

    @Column(name = "check_out_time",nullable = false)
    private LocalDateTime checkOutTime;

    @Column(name = "expected_arrival_date",nullable = false)
    private LocalDate expectedArrivalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "form_status", columnDefinition = "ENUM('Pending', 'ParentApproved', 'WardenApproved')")
    private FormStatus formStatus;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    public enum FormStatus {
        PENDING, PARENT_APPROVED, WARDEN_APPROVED
    }
}

