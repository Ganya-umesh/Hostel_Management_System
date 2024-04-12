package com.example.hostel_management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final HttpSession httpSession;

    @Autowired
    public DashboardController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping
    public String showDashboard(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        if (oauth2User != null) {
            String userName = oauth2User.getAttribute("name"); // Assuming "name" is the attribute representing the user's name
            System.out.println("User's name: " + userName);

            // Add the user's name to the model for displaying in the view
            model.addAttribute("userName", userName);
        } else {
            // Retrieve username from the session for normal login
            String username = (String) httpSession.getAttribute("username");
            model.addAttribute("username", username);
        }
        return "dashboard";
    }
}
