package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WardenService {
    private final WardenRepository wardenRepository;

    @Autowired
    public WardenService(WardenRepository wardenRepository) {
        this.wardenRepository = wardenRepository;
    }
    public Warden saveWarden(Warden warden) {
        return wardenRepository.save(warden);
    }
    public Warden getWardenById(Long id) {
        return wardenRepository.findById(id).orElse(null);
    }
}
