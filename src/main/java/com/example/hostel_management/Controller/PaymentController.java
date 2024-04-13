package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Payment;
import com.example.hostel_management.Service.PaymentService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/warden/{wardenId}/raise-for-all")
    public ResponseEntity<String> raisePaymentRequestForAllHostellers(@PathVariable Long wardenId, @RequestBody PaymentRequestDTO paymentRequestDTO) {
        Payment paymentRequest = new Payment();
        paymentRequest.setAmount(paymentRequestDTO.getAmount());
        paymentRequest.setTransactionType(paymentRequestDTO.getTransactionType());
        paymentRequest.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentService.raisePaymentRequestForAllHostellers(wardenId, paymentRequest);
        return new ResponseEntity<>("Payment request raised successfully for all hostellers", HttpStatus.CREATED);
    }
    @Data
    private static class PaymentRequestDTO {
        private Long amount;
        private Payment.TransactionType transactionType;
        private Payment.PaymentMode paymentMode;
    }



    @PutMapping("/hosteller/{hostellerId}/payment/{paymentId}")
    public ResponseEntity<Payment> makePayment(@PathVariable Long hostellerId, @PathVariable Long paymentId, @RequestBody PaymentRequest request) {
        Payment payment = paymentService.makePayment(hostellerId, paymentId, request.getTransactionDate(), request.getPaymentMode());
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Data
    private static class PaymentRequest {
        private String transactionDate;
        private Payment.PaymentMode paymentMode;
    }




    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
