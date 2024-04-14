package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    //we are not adding @repository annotation because we are extending JpaRepository. it internally contains @Repository annotation
}
