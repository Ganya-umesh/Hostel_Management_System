package com.example.hostel_management.Service;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Repository.HostellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostellerService {
     private final HostellerRepository hostellerRepository;


     //no need to autowire the constructor because spring 4.3 onwards whenever there is only a single constructor in the class then autowiring happens implicitly
    public HostellerService(HostellerRepository hostellerRepository) {
        this.hostellerRepository = hostellerRepository;
    }

    public Hosteller saveHosteller(Hosteller hosteller){
        return hostellerRepository.save(hosteller);
    }

    public Hosteller getHostellerById(Long id){
        return hostellerRepository.findById(id).orElse(null);
    }

    public List<Hosteller> getAllHostellers(){
        return hostellerRepository.findAll();
    }



//    public ResponseEntity<String> deleteHostellerById(Long id) {
//        Optional<Hosteller> hostellerOptional = hostellerRepository.findById(id);
//        if (hostellerOptional.isPresent()) {
//            hostellerRepository.deleteById(id);
//            String message = "Hosteller with id " + id + " has been successfully deleted.";
//            return ResponseEntity.ok().body(message);
//        } else {
//            String message = "Hosteller with id " + id + " does not exist.";
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
//        }
//    }

}
