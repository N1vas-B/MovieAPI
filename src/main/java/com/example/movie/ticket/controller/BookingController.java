package com.example.movie.ticket.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.movie.ticket.dao.Booking;
import com.example.movie.ticket.dao.BookingStatus;
import com.example.movie.ticket.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/bookedmovieslist")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

   
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

  //  @PreAuthorize("hasRole('USER')")
    @PostMapping("/bookticket")
    public Booking createBooking(@RequestParam Long userId,
                                 @RequestParam Long movieId,
                                 @RequestParam List<String> seatNumbers) {
        return bookingService.createBooking(userId, movieId, seatNumbers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public Booking updateBookingStatus(@PathVariable Long id,
                                       @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }
}
