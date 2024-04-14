package com.example.hostel_management.Service;

import com.example.hostel_management.Model.RoomAllocation;
import com.example.hostel_management.Repository.RoomAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAllocationService {

    private final RoomAllocationRepository roomAllocationRepository;

    @Autowired
    public RoomAllocationService(RoomAllocationRepository roomAllocationRepository) {
        this.roomAllocationRepository = roomAllocationRepository;
    }

    public void bookRoom(RoomAllocation roomAllocation) {
        roomAllocationRepository.save(roomAllocation);
    }

    public RoomAllocation findRoomAllocationByUsername(String username) {
        return roomAllocationRepository.findRoomAllocationByName(username);
    }

    public List<RoomAllocation> getAllRoomAllocations() {
        return roomAllocationRepository.findAll();
    }
}
