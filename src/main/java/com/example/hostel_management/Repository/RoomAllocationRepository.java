package com.example.hostel_management.Repository;

import com.example.hostel_management.Model.RoomAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAllocationRepository extends JpaRepository<RoomAllocation, Long> {
    RoomAllocation findRoomAllocationByName(String name);
}
