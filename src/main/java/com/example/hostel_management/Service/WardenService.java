package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WardenService {
    private final WardenRepository wardenRepository;

    @Autowired
    public WardenService(WardenRepository wardenRepository) {
        this.wardenRepository = wardenRepository;
    }
    public Warden getWardenById(Long id) {
        return wardenRepository.findById(id).orElse(null);
    }

    public void saveWarden(Warden warden) {
        // Hash the password before storing it
        String hashedPassword = hashPassword(warden.getPassword());
        warden.setPassword(hashedPassword);

        // Save the login object with hashed password
        wardenRepository.save(warden);
    }

    // Helper method to hash the password
    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
