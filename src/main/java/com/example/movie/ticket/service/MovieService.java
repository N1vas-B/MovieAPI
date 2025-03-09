package com.example.movie.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.ticket.dao.Movie;
import com.example.movie.ticket.dto.MovieDto;
import com.example.movie.ticket.repository.MovieRepository;

@Service
public class MovieService
{
    @Autowired
    private MovieRepository movieRepository;
    
    public List<Movie> getAllMovies()
    {
        return movieRepository.findAll();
    }
 

public Optional<Movie> getMovieById(Long id) {
    return movieRepository.findById(id);
}

public Movie addMovie(Movie movie) {
    return movieRepository.save(movie);
}

public Movie addMovie(MovieDto movieDto) {
    Movie movie = new Movie();
    movie.setTitle(movieDto.getTitle());
    movie.setGenre(movieDto.getGenre());
    movie.setDirector(movieDto.getDirector());
    movie.setReleaseDate(movieDto.getReleaseDate());
    movie.setDuration(movieDto.getDuration());
    movie.setPricePerTicket(movieDto.getPricePerTicket());
    movie.setDescription(movieDto.getDescription());
    movie.setRating(movieDto.getRating());
    movie.setAvailableShowtimes(movieDto.getAvailableShowtimes());

    return movieRepository.save(movie);
}

public Movie updateMovie(Long id, Movie movieDetails) {
    Movie movie = movieRepository.findById(id).orElseThrow();
    movie.setTitle(movieDetails.getTitle());
    movie.setGenre(movieDetails.getGenre());
    movie.setDirector(movieDetails.getDirector());
    movie.setReleaseDate(movieDetails.getReleaseDate());
    movie.setDuration(movieDetails.getDuration());
    movie.setPricePerTicket(movieDetails.getPricePerTicket());
    movie.setDescription(movieDetails.getDescription());
    movie.setRating(movieDetails.getRating());
    movie.setAvailableShowtimes(movieDetails.getAvailableShowtimes());
    return movieRepository.save(movie);
}

public void deleteMovie(Long id)
{
    Movie movie = movieRepository.findById(id).orElseThrow();
    movieRepository.delete(movie);
}
    
}
