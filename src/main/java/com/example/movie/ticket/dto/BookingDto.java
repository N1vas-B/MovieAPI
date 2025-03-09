package com.example.movie.ticket.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingDto {
    private Long userId;          // ID of the user booking the ticket
    private Long movieId;         // ID of the movie being booked
    private List<String> seatNumbers; // List of booked seat numbers
    private Double totalPrice;    // Total ticket price
    private String bookingStatus; // Booking status (Confirmed, Pending, Cancelled)
    private String paymentStatus; // Payment status (Paid, Pending)
}
