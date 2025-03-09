package com.example.movie.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.movie.ticket.dao.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>
{
	
}