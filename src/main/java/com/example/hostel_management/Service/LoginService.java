package com.example.hostel_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hostel_management.Model.Login;
import com.example.hostel_management.Repository.LoginRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean authenticate(String username, String password) {
        Login user = loginRepository.findByUsername(username);
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false; // User not found or incorrect password
    }

    public List<Login> getAllLogins() {
        return loginRepository.findAll();
    }

    public Login getLoginById(Long id) {
        return loginRepository.findById(id).orElse(null);

    }

    public Login findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }

    public void saveLogin(Login login) {
        // Hash the password before storing it
        String hashedPassword = hashPassword(login.getPassword());
        login.setPassword(hashedPassword);

        // Save the login object with hashed password
        loginRepository.save(login);
    }

    // Helper method to hash the password
    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public void deleteLogin(Long id) {
        loginRepository.deleteById(id);
    }
}
