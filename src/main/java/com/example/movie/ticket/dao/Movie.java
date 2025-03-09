package com.example.movie.ticket.dao;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String director;
    private LocalDateTime releaseDate;
    private String duration;
    private double pricePerTicket;
    private String description;
    private double rating;
    
    @ElementCollection
    private List<LocalDateTime> availableShowtimes;

	
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public double getPricePerTicket() {
		return pricePerTicket;
	}

	public void setPricePerTicket(double pricePerTicket) {
		this.pricePerTicket = pricePerTicket;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<LocalDateTime> getAvailableShowtimes() {
		return availableShowtimes;
	}

	public void setAvailableShowtimes(List<LocalDateTime> availableShowtimes) {
		this.availableShowtimes = availableShowtimes;
	}

	
	
}