package com.example.movie.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.ticket.dao.Booking;
import com.example.movie.ticket.dao.User;

public interface BookingRepository extends JpaRepository<Booking, Long>
{
	List<Booking> findByUser(User user);
}
