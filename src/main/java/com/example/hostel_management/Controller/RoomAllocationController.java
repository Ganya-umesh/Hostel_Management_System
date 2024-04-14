package com.example.hostel_management.Controller;

import com.example.hostel_management.Model.RoomAllocation;
import com.example.hostel_management.Service.RoomAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/api/roomallocation")
public class RoomAllocationController {

    private final RoomAllocationService roomAllocationService;
    private final HttpSession httpSession;

    @Autowired
    public RoomAllocationController(RoomAllocationService roomAllocationService, HttpSession httpSession) {
        this.roomAllocationService = roomAllocationService;
        this.httpSession = httpSession;
    }

    @PostMapping("/book")
    public String bookRoom(@RequestParam("bookingTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bookingTime) {
        try {
            // Retrieve session attributes
            String username = (String) httpSession.getAttribute("username");
            String email = (String) httpSession.getAttribute("email");
            int year = (int) httpSession.getAttribute("year");

//            System.out.println("username from session is " + username);
//            System.out.println("email from session is " + email);
//            System.out.println("year from session is " + year);

            RoomAllocation existingRoomAllocation = roomAllocationService.findRoomAllocationByUsername(username);
            if (existingRoomAllocation != null) {
                return "redirect:/error?message=User already booked a room";
            }

            RoomAllocation roomAllocation = new RoomAllocation();
            roomAllocation.setName(username);
            roomAllocation.setEmail(email);
            roomAllocation.setYear(year);
            roomAllocation.setBookingTime(bookingTime);

            System.out.println("room allocation details " + roomAllocation);

            roomAllocationService.bookRoom(roomAllocation);

            return "redirect:/hosteller/dashboard";
        } catch (Exception e) {
            // Handle error
            System.err.println("Error occurred while booking room: " + e.getMessage());
            return "error";
        }
    }
    @GetMapping("/allocate")
    public String allocatePriority() {
        // Retrieve all room allocations
        List<RoomAllocation> roomAllocations = roomAllocationService.getAllRoomAllocations();

        // Sort room allocations by year and booking time
        roomAllocations.sort(
                Comparator.comparing(RoomAllocation::getYear, Comparator.reverseOrder())
                        .thenComparing(RoomAllocation::getBookingTime)
        );

        // Assign priorities based on order
        int priority = 1;
        for (RoomAllocation roomAllocation : roomAllocations) {
            roomAllocation.setPriority(priority);
            roomAllocationService.bookRoom(roomAllocation);
            priority++;
        }

        return "redirect:/warden/dashboard";
    }

}
