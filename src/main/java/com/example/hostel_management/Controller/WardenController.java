package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.Payment;
import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.WardenService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warden")
public class WardenController {
    private static final Logger logger = LoggerFactory.getLogger(WardenController.class);

    private final WardenService wardenService;
    private final HostellerService hostellerService;

    public WardenController(WardenService wardenService, HostellerService hostellerService) {
        this.wardenService = wardenService;
        this.hostellerService = hostellerService;
    }

    @PostMapping(value = "")
    public Warden saveWarden(@RequestBody Warden warden) {
        if (warden == null) {
            logger.error("Incoming request payload is null");
            throw new IllegalArgumentException("Incoming request payload is null");
        }

        logger.info("Incoming request payload: {}", warden);

        if (warden.getEmail() == null || warden.getEmail().isEmpty()) {
            String errorMessage = "Email is null or empty in the request payload";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        return wardenService.saveWarden(warden);
    }

    @GetMapping(value = "/{id}")
    public Warden getWardenById(@PathVariable Long id) {
        return wardenService.getWardenById(id);
    }

    @DeleteMapping("/{wardenId}/remove-hosteller")
    public ResponseEntity<String> removeHosteller(
            @PathVariable Long wardenId,
            @RequestBody HostellerIdRequest hostellerIdRequest
    ) {
        // Check if the provided wardenId exists
        if (wardenService.getWardenById(wardenId) == null) {
            throw new IllegalArgumentException("Warden with ID " + wardenId + " does not exist.");
        }

        Hosteller hosteller = hostellerService.getHostellerById(hostellerIdRequest.getHostellerId());
        if (hosteller == null) {
            throw new IllegalArgumentException("Hosteller with ID " + hostellerIdRequest.getHostellerId() + " does not exist.");
        }

        List<Payment> payments = hosteller.getPaymentslist();
        for (Payment payment : payments) {
            payment.setHosteller(null);
        }
        hostellerService.removeHosteller(hostellerIdRequest.getHostellerId());

        return ResponseEntity.ok().body("Hosteller removed successfully.");
    }

    @Data
    static class HostellerIdRequest {
        private Long hostellerId;
    }
}
