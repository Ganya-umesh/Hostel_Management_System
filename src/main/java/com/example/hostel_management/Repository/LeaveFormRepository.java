package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.LeaveForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveFormRepository extends JpaRepository<LeaveForm, Long> {
    List<LeaveForm> findByHosteller(String hosteller);
}
