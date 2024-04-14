package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.Hosteller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostellerRepository extends JpaRepository<Hosteller, Long> {
    Hosteller findByUsername(String username);
    //we are not adding @repository annotation because we are extending JpaRepository. it internally contains @Repository annotation
}
