package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Service.HostellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosteller")
public class HostellerController {
    private final HostellerService hostellerService;

    public HostellerController(HostellerService hostellerService) {
        this.hostellerService = hostellerService;
    }

    @PostMapping(value = "")
    public Hosteller saveHosteller(@RequestBody Hosteller hosteller){
        return hostellerService.saveHosteller(hosteller);
    }

    @GetMapping(value = "/{id}")
    public Hosteller getHostellerById(@PathVariable Long id){
        return hostellerService.getHostellerById(id);
    }

    @GetMapping(value = "")
    public List<Hosteller> getAllHostellers(){
        return hostellerService.getAllHostellers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hosteller> updateHosteller(@PathVariable Long id, @RequestBody Hosteller hosteller) {
        return hostellerService.updateHosteller(id, hosteller);
    }
}
