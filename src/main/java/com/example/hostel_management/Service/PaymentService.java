package com.example.hostel_management.Service;


import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.Payment;
import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Repository.HostellerRepository;
import com.example.hostel_management.Repository.PaymentRepository;
import com.example.hostel_management.Repository.WardenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final HostellerRepository hostellerRepository;
    private final WardenRepository wardenRepository;

    public PaymentService(PaymentRepository paymentRepository, HostellerRepository hostellerRepository, WardenRepository wardenRepository) {
        this.paymentRepository = paymentRepository;
        this.hostellerRepository = hostellerRepository;
        this.wardenRepository = wardenRepository;
    }

    public void raisePaymentRequestForAllHostellers(Long wardenId, Payment paymentRequest) {
        Warden warden = wardenRepository.findById(wardenId)
                .orElseThrow(() -> new RuntimeException("Warden not found with id: " + wardenId));

        List<Hosteller> hostellers = hostellerRepository.findAll();

        for (Hosteller hosteller : hostellers) {
            Payment payment = new Payment();
            payment.setAmount(paymentRequest.getAmount());
            payment.setTransactionDate(paymentRequest.getTransactionDate());
            payment.setTransactionType(paymentRequest.getTransactionType());
            payment.setPaymentStatus(paymentRequest.getPaymentStatus());
            payment.setPaymentMode(paymentRequest.getPaymentMode());
            payment.setHosteller(hosteller);
            payment.setWarden(warden);
            paymentRepository.save(payment);
        }
    }
    public Payment makePayment(Long hostellerId, Long paymentId, String transactionDate, Payment.PaymentMode paymentMode) {
        Hosteller hosteller = hostellerRepository.findById(hostellerId)
                .orElseThrow(() -> new RuntimeException("Hosteller not found with id: " + hostellerId));

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        if (!payment.getHosteller().equals(hosteller)) {
            throw new RuntimeException("This payment request is not for the given hosteller");
        }

        if (payment.getPaymentStatus() != Payment.PaymentStatus.PENDING) {
            throw new RuntimeException("This payment has already been processed");
        }

        payment.setPaymentStatus(Payment.PaymentStatus.HOSTELLER_CONFIRMED_PAYMENT);
        payment.setTransactionDate(transactionDate);
        payment.setPaymentMode(paymentMode);
        return paymentRepository.save(payment);
    }
    public Payment verifyPayment(Long wardenId, Long paymentId, Payment.PaymentStatus paymentStatus) {
        Warden warden = wardenRepository.findById(wardenId)
                .orElseThrow(() -> new RuntimeException("Warden not found with id: " + wardenId));

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        if (!payment.getWarden().equals(warden)) {
            throw new RuntimeException("This payment is not associated with the given warden");
        }

        if (payment.getPaymentStatus() != Payment.PaymentStatus.HOSTELLER_CONFIRMED_PAYMENT) {
            throw new RuntimeException("This payment cannot be verified as the hosteller has not confirmed the payment yet");
        }

        payment.setPaymentStatus(paymentStatus);
        return paymentRepository.save(payment);
    }
}

