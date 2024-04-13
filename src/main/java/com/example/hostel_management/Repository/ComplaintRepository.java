package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findAllByStatus(Complaint.ComplaintStatus status);
}
