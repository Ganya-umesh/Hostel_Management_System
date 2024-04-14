package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.ParentService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final HostellerService hostellerService;
    private final ParentService parentService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    public HomeController(HostellerService hostellerService, ParentService parentService) {
        this.hostellerService = hostellerService;
        this.parentService = parentService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/saveHosteller")
    public String saveHosteller(@ModelAttribute Hosteller hosteller, HttpSession session) {

        boolean f = hostellerService.UserExists(hosteller.getEmail());
        if(f) {
            session.setAttribute("msg", "User already exists with this email");
        } else {
            Hosteller savedHosteller = hostellerService.saveHosteller(hosteller);
            if (savedHosteller != null) {
                session.setAttribute("msg", "Hosteller registered successfully");
            } else {
                session.setAttribute("msg", "Registration failed");
            }

        }
        return "register";
    }
}
