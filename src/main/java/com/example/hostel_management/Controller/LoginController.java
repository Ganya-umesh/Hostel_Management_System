package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Login;
import com.example.hostel_management.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Use @Controller annotation for Thymeleaf controller
@RequestMapping("/login") // Remove "/api" from the request mapping
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/loginForm")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Login()); // Add an empty Login object to the model
        return "login"; // Return the name of the HTML template file without extension
    }

    @GetMapping("/registerForm")
    public String showRegisterForm(Model model) {
        model.addAttribute("register", new Login()); // Add an empty Login object to the model
        return "register"; // Return the name of the HTML template file without extension
    }

    @PostMapping("/loginSubmit")
    public String loginSubmit(@ModelAttribute Login login) {
        String username = login.getUsername();
        String password = login.getPassword();

        System.out.println("username "+username);
        System.out.println("password "+password);

        if (loginService.authenticate(username, password)) {
            // Authentication successful, redirect to dashboard
            return "redirect:/dashboard";
        } else {
            // Authentication failed, redirect back to login form with error message
            return "redirect:/login/loginForm?error";
        }
    }

    @PostMapping("/registerSubmit")
    public String register(@ModelAttribute Login login) {
        try {
            loginService.saveLogin(login);
            // Implement registration logic here (e.g., validate and save credentials)
            // Return appropriate response based on registration success or failure
            // For simplicity, let's return a success message
            return "redirect:/dashboard"; // Redirect to dashboard page after successful registration
        } catch (Exception e) {
            // Log the error
            System.err.println("Error occurred while registering: " + e.getMessage());
            // Redirect to an error page or return an appropriate error message
            return "error";
        }
    }
}

