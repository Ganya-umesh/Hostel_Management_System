package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.Parent;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hosteller")
public class HostellerController {
    private final HostellerService hostellerService;
    private final ParentService parentService;

    public HostellerController(HostellerService hostellerService, ParentService parentService) {
        this.hostellerService = hostellerService;
        this.parentService = parentService;
    }

    @PostMapping(value = "")
    public Hosteller saveHosteller(@RequestBody Hosteller hosteller) {
        if (hosteller.getPhoneNumber() == null || hosteller.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required.");
        }
        Parent parent = hosteller.getParent();


        Hosteller savedHosteller = hostellerService.saveHosteller(hosteller);

        Parent savedParent = parentService.saveParent(parent);
        savedHosteller.setParent(savedParent);

        return savedHosteller;
    }



    @GetMapping(value = "/{id}")
    public Hosteller getHostellerById(@PathVariable Long id) {

        return hostellerService.getHostellerById(id);
    }

    @GetMapping(value = "")
    public List<Hosteller> getAllHostellers() {
        return hostellerService.getAllHostellers();
    }

    //needs to be fixed

    @PutMapping("/{id}")
    public ResponseEntity<Hosteller> updateHosteller(@PathVariable Long id, @RequestBody Hosteller hosteller) {
        return hostellerService.updateHosteller(id, hosteller);
    }
}