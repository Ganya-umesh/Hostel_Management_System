package com.example.hostel_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hosteller/dashboard")
public class HostellerDashboardController {

    private final HttpSession httpSession;

    @Autowired
    public HostellerDashboardController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping
    public String showDashboard(@AuthenticationPrincipal Model model) {
        String username = (String) httpSession.getAttribute("username");
        model.addAttribute("username", username);
        return "hostellerDashboard";
    }
}
