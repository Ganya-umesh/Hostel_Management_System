package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Hosteller;
import com.example.hostel_management.Model.User;
import com.example.hostel_management.Model.Warden;
import com.example.hostel_management.Service.HostellerService;
import com.example.hostel_management.Service.UserService; // Import UserService
import com.example.hostel_management.Service.WardenService; // Import WardenService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final HostellerService hostellerService;
    private final UserService userService;
    private final WardenService wardenService;


    @Autowired
    public LoginController(HostellerService loginService, UserService userService, WardenService wardenService) {
        this.hostellerService = loginService;
        this.userService = userService;
        this.wardenService = wardenService;
    }

    @GetMapping("/loginForm")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Hosteller());
        return "login";
    }

    @GetMapping("/registerForm")
    public String showRegisterForm(Model model) {
        model.addAttribute("register", new Hosteller());
        return "register";
    }

    @GetMapping("/registerFormWarden")
    public String showRegisterFormWarden(Model model) {
        model.addAttribute("registerWarden", new Warden());
        return "registerWarden";
    }
    @PostMapping("/loginSubmit")
    public String loginSubmit(@ModelAttribute User user, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println("password is "+password);

        if (userService.authenticate(username, password)) {
            session.setAttribute("username", username);
            // Assuming you have a method to retrieve user by username
            User user2 = userService.findByUsername(username);
            String role = user2.getRole();

            // Redirect based on user's role
            if ("ROLE_USER".equals(role)) {
                return "redirect:/hosteller/dashboard";
            } else if ("ROLE_WARDEN".equals(role)) {
                return "redirect:/warden/dashboard";
            } else {
                return "redirect:/login/loginForm?errors"; // Invalid role
            }
        } else {
            return "redirect:/login/loginForm?errorrrr"; // Authentication failed
        }
    }

    @PostMapping("/registerSubmit")
    public String register(@ModelAttribute Hosteller hosteller, HttpSession session) {
        try {
            hostellerService.saveLogin(hosteller);
            session.setAttribute("username", hosteller.getUsername());
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.err.println("Error occurred while registering: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/registerSubmitWarden")
    public String registerWarden(@ModelAttribute Warden warden) {
        try {
             wardenService.saveWarden(warden);
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.err.println("Error occurred while registering Warden: " + e.getMessage());
            return "error";
        }
    }


//    @GetMapping("/oauth2/authorization/google")
//    public String initiateOAuth2Login(HttpSession session) {
//        String redirectUrl = "https://accounts.google.com/o/oauth2/auth";
//        String clientId = "674245147101-71semnplg8hn3mud1bckmdfe9g8r5q77.apps.googleusercontent.com";
//        String redirectUri = "http://localhost:8080/dashboard";
//        String scope = "openid profile email"; // Define your required scopes
//
//        // Build the redirect URL with required parameters
//        redirectUrl += "?response_type=code" +
//                "&client_id=" + clientId +
//                "&redirect_uri=" + redirectUri +
//                "&scope=" + scope;
//
//        // Store a flag in session indicating that the user is logging in via OAuth
//        session.setAttribute("oauth_login", true);
//
//        // Print the flag value for debugging
//        System.out.println("OAuth login flag set: " + session.getAttribute("oauth_login"));
//
//        return "redirect:" + redirectUrl;
//    }


    @GetMapping("/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/login/loginForm";
    }
}
