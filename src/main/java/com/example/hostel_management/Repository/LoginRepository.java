package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUsername(String username);

}
