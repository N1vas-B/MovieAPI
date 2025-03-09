package com.example.movie.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.ticket.dao.Booking;
import com.example.movie.ticket.dao.BookingStatus;
import com.example.movie.ticket.dao.Movie;
import com.example.movie.ticket.dao.PaymentStatus;
import com.example.movie.ticket.dao.User;
import com.example.movie.ticket.exception.ResourceNotFoundException;
import com.example.movie.ticket.repository.BookingRepository;
import com.example.movie.ticket.repository.MovieRepository;
import com.example.movie.ticket.repository.UserRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public Booking createBooking(Long userId, Long movieId, List<String> seatNumbers) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        double ticketPrice = movie.getPricePerTicket() * seatNumbers.size();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMovie(movie);
        booking.setSeatNumbers(seatNumbers);
        booking.setTicketPrice(ticketPrice);
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.UNPAID);
        booking.setBookingDate(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public Booking updateBookingStatus(Long id, BookingStatus status)
    {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}

