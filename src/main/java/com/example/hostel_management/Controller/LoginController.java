package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.Login;
import com.example.hostel_management.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Controller
@RequestMapping("/login")
public class    LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/loginForm")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/registerForm")
    public String showRegisterForm(Model model) {
        model.addAttribute("register", new Login());
        return "register";
    }

    @PostMapping("/loginSubmit")
    public String loginSubmit(@ModelAttribute Login login, HttpSession session,Model model) {
        String username = login.getUsername();
        String password = login.getPassword();

        System.out.println("username " + username);
        System.out.println("password " + password);

        if (loginService.authenticate(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/dashboard";
        } else {
            return "redirect:/login/loginForm?error";
        }
    }

    @PostMapping("/registerSubmit")
    public String register(@ModelAttribute Login login, HttpSession session,Model model) {
        try {
            loginService.saveLogin(login);
            session.setAttribute("username", login.getUsername());
            model.addAttribute("username", login.getUsername());
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.err.println("Error occurred while registering: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/oauth2/authorization/google")
    public String initiateOAuth2Login(HttpSession session) {
        String redirectUrl = "https://accounts.google.com/o/oauth2/auth";
        String clientId = "674245147101-71semnplg8hn3mud1bckmdfe9g8r5q77.apps.googleusercontent.com";
        String redirectUri = "http://localhost:8080/dashboard";
        String scope = "openid profile email"; // Define your required scopes

        // Build the redirect URL with required parameters
        redirectUrl += "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=" + scope;

        // Store a flag in session indicating that the user is logging in via OAuth
        session.setAttribute("oauth_login", true);

        // Print the flag value for debugging
        System.out.println("OAuth login flag set: " + session.getAttribute("oauth_login"));

        return "redirect:" + redirectUrl;
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/login/loginForm";
    }
}
