package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Service.WardenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warden")
public class WardenController {
    private static final Logger logger = LoggerFactory.getLogger(WardenController.class);

    private final WardenService wardenService;

    public WardenController(WardenService wardenService) {
        this.wardenService = wardenService;
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
}
