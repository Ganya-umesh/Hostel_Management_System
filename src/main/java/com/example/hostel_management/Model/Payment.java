package com.example.hostel_management.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column()
    private String TransactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    public enum PaymentStatus {
        PENDING,SUCCESS,FAILED
    }

    public enum TransactionType {
        HOSTEL_RENT, MESS_FEES, MAINTENANCE, OTHERS
    }

    public enum PaymentMode {
        CARD, UPI, NETBANKING
    }

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Hosteller.class)
    @JoinColumn(name = "hosteller_id")
    @JsonBackReference(value = "hosteller-payments")
    private Hosteller hosteller;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Warden.class)
    @JoinColumn(name = "warden_id")
    @JsonBackReference(value = "warden-payments")
    private Warden warden;

}
