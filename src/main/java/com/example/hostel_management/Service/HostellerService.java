package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Repository.HostellerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostellerService {
    private final HostellerRepository hostellerRepository;

    @PersistenceContext
    private EntityManager entityManager;


    //no need to autowire the constructor because spring 4.3 onwards whenever there is only a single constructor in the class then autowiring happens implicitly
    @Autowired
    public HostellerService(HostellerRepository hostellerRepository) {
        this.hostellerRepository = hostellerRepository;
    }

    public Hosteller saveHosteller(Hosteller hosteller) {
        return hostellerRepository.save(hosteller);
    }

    public Hosteller getHostellerById(Long id) {
        return hostellerRepository.findById(id).orElse(null);
    }

    public List<Hosteller> getAllHostellers() {
        return hostellerRepository.findAll();
    }


    public ResponseEntity<Hosteller> updateHosteller(Long id, Hosteller hosteller) {
        Hosteller hostellerToBeUpdated = hostellerRepository.findById(id).orElse(null);
        if (hostellerToBeUpdated == null) {
            return ResponseEntity.notFound().build();
        }

        // Copy non-null properties from the request object to the entity object
        BeanUtils.copyProperties(hosteller, hostellerToBeUpdated, "id");

        Hosteller updatedHosteller = hostellerRepository.save(hostellerToBeUpdated);
        return ResponseEntity.ok(updatedHosteller);
    } ;

    public ResponseEntity<String> removeHosteller(Long hostellerId) {
        Optional<Hosteller> hostellerOptional = hostellerRepository.findById(hostellerId);
        if (hostellerOptional.isPresent()) {
            hostellerRepository.deleteById(hostellerId);
            String message = "Hosteller with id " + hostellerId + " has been successfully deleted.";
            return ResponseEntity.ok().body(message);
        } else {
            String message = "Hosteller with id " + hostellerId + " does not exist.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public Hosteller authenticateHosteller(String email, String password) {
        Hosteller hosteller = hostellerRepository.findByEmail(email);

        if (hosteller != null && hosteller.getPassword().equals(password)) {
            return hosteller;
        }

        return null;
    }

}

