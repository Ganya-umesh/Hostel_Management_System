package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "payment_by_hosteller_id")
//    private Long hostellerId;

//    @Column(name = "payment_approved_bywarden_id")
//    private Long wardenId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String TransactionDate;

    @Column(nullable = false)
    private String TransactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "ENUM('Pending', 'SUCCESS','FAILED')")
    private PaymentStatus paymentStatus;

    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hosteller", insertable = false, updatable = false)
    @JsonBackReference(value = "hosteller-payment")
    private Hosteller hosteller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warden")
    @JsonBackReference(value = "warden-payments")
    private Warden warden;




}
