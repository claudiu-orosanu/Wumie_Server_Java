package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.User;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    void updateMovie(Movie movie);
    Movie createMovie(MovieDto movie);
    Movie addActorsToMovie(Movie movie, List<Long> actorIds);
    void deleteMovieById(Long id);

    void addMovieToWatchlist(Movie movie, User user);

    void removeMovieFromWatchlist(Movie movie, User user);
}
