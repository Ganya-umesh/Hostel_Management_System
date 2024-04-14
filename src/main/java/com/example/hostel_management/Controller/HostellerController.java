package com.example.hostel_management.Controller;

import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.ParentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hosteller")
public class HostellerController {
    private final HostellerService hostellerService;
    private final ParentService parentService;

    public HostellerController(HostellerService hostellerService, ParentService parentService) {
        this.hostellerService = hostellerService;
        this.parentService = parentService;
    }

    @GetMapping("/")
    public String home() {
        return "hosteller/home";
    }

}