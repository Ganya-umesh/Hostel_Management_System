//package com.example.hostel_management.Controller;
//
//import org.springframework.web.bind.annotation.*;
//import java.util.PriorityQueue;
//
//@RestController
//@RequestMapping("/hostel")
//public class RoomAllocationController {
//
//    // PriorityQueue to store student details based on priority
//    private PriorityQueue<StudentBooking> bookingQueue = new PriorityQueue<>();
//
//    // API endpoint to add a new booking
//    @PostMapping("/book")
//    public String bookRoom(@RequestBody StudentBooking studentBooking) {
//        bookingQueue.offer(studentBooking); // Add the booking to the priority queue
//        return "Booking successful for " + studentBooking.getName();
//    }
//
//    // API endpoint to get the next booking
//    @GetMapping("/nextBooking")
//    public StudentBooking getNextBooking() {
//        return bookingQueue.peek(); // Return the next booking without removing it from the queue
//    }
//
//    // StudentBooking class to represent a student's booking details
//    static class StudentBooking implements Comparable<StudentBooking> {
//        private String name;
//        private int year;
//        private long bookingTime; // Assuming booking time is in milliseconds
//
//        // Constructor
//        public StudentBooking(String name, int year, long bookingTime) {
//            this.name = name;
//            this.year = year;
//            this.bookingTime = bookingTime;
//        }
//
//        // Implement compareTo method to compare bookings based on priority
//        @Override
//        public int compareTo(StudentBooking other) {
//            if (this.year != other.year) {
//                return Integer.compare(other.year, this.year); // Higher year gets higher priority
//            } else {
//                return Long.compare(this.bookingTime, other.bookingTime); // FCFS within the same year
//            }
//        }
//
//        // Getters and setters
//        // (Omitted for brevity)
//    }
//}
//
