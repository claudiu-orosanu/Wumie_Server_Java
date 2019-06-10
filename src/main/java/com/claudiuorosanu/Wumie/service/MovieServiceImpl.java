package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.converters.DtoToMovieConverter;
import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.exception.AppException;
import com.claudiuorosanu.Wumie.exception.ResourceNotFoundException;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.ActorMovie;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.User;
import com.claudiuorosanu.Wumie.repository.ActorMovieRepository;
import com.claudiuorosanu.Wumie.repository.ActorRepository;
import com.claudiuorosanu.Wumie.repository.MovieRepository;
import com.claudiuorosanu.Wumie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorMovieRepository actorMovieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoToMovieConverter dtoToMovieConverter;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies;
    }

    @Override
    public Movie getMovieById(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("movie", "id", id.toString()));

        return movie;
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Movie createMovie(MovieDto movieDto) {
        Movie movie = dtoToMovieConverter.convert(movieDto);
        return movieRepository.save(movie);
    }

    @Override
    public Movie addActorsToMovie(Movie movie, List<Long> actorIds) {
        List<Actor> actorList = actorRepository.findAllById(actorIds);
        for (Actor actor : actorList) {

            if (actorMovieRepository.existsByActor_IdAndMovie_Id(actor.getId(), movie.getId())) {
                continue;
            }

            movie.getActorMovies().add(
                new ActorMovie(movie, actor)
            );
        }

        movieRepository.save(movie);
        return movie;
    }

    @Override
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void addMovieToWatchlist(Movie movie, User user) {
        user.getWatchlist().add(movie);
        userRepository.save(user);
    }

    @Override
    public void removeMovieFromWatchlist(Movie movie, User user) {
        user.getWatchlist().remove(movie);
        userRepository.save(user);
    }
}
