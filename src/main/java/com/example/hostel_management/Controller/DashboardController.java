package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Login;
import com.example.hostel_management.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String showDashboard() {
        return "dashboard"; // Return the name of the HTML template file for the dashboard
    }
}