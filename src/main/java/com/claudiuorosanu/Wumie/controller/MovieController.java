package com.claudiuorosanu.Wumie.controller;


import com.claudiuorosanu.Wumie.converter.ActorToDtoConverter;
import com.claudiuorosanu.Wumie.converter.MovieToDtoConverter;
import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.exception.ResourceNotFoundException;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.User;
import com.claudiuorosanu.Wumie.payload.request.AddActorsToMovieRequest;
import com.claudiuorosanu.Wumie.security.CurrentUser;
import com.claudiuorosanu.Wumie.security.UserPrincipal;
import com.claudiuorosanu.Wumie.service.ActorService;
import com.claudiuorosanu.Wumie.service.MovieService;
import com.claudiuorosanu.Wumie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieToDtoConverter movieToDtoConverter;
    @Autowired
    private ActorToDtoConverter actorToDtoConverter;

    @GetMapping
    // GET /api/movies
    public List<MovieDto> getMovies(@CurrentUser UserPrincipal currentUserPrincipal) {

        List<Movie> movies = movieService.getAllMovies();

        return movies
            .stream()
            .map(movie -> {
                // compute actors count for each movie
                int actorsCount = movie.getActorMovies().size();
                // check if movie has been added to the watchlist by user
                boolean isWatched = movie.getUsersToWatch()
                    .stream()
                    .anyMatch(
                        user -> user.getId().equals(currentUserPrincipal.getId())
                    );

                MovieDto movieDto = movieToDtoConverter.convert(movie);
                movieDto.setActorsCount(actorsCount);
                movieDto.setWatched(isWatched);
                return movieDto;
            })
            .collect(Collectors.toList());
    }

    // GET /api/movies/5
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public MovieDto getMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return movieToDtoConverter.convert(movie);
    }

    // GET /api/movies/5/actors
    @RequestMapping(value = "{id}/actors", method = RequestMethod.GET)
    public List<ActorDto> getActorsForMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        List<Actor> actors = actorService.getActorsForMovie(movie);

        return actors.stream()
                     .map(actorToDtoConverter::convert)
                     .collect(Collectors.toList());
    }

    // PUT /api/movies/5
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie) {
        movie.setId(id);
        movieService.updateMovie(movie);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // POST /api/movies
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDto movieDto) {
        Movie createdMovie = movieService.createMovie(movieDto);
        MovieDto createdMovieDto = movieToDtoConverter.convert(createdMovie);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{movieId}")
                .buildAndExpand(createdMovieDto.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdMovieDto);
    }

    // POST api/movies/5/actors
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{id}/actors", method = RequestMethod.POST)
    public ResponseEntity addActorsToMovie(
            @PathVariable Long id,
            @RequestBody AddActorsToMovieRequest addActorsToMovieRequest
    ) {
        Movie movie = movieService.getMovieById(id);
        int noOfAddedActors = movieService.addActorsToMovie(movie, addActorsToMovieRequest.getActorIds());
        return ResponseEntity.ok(noOfAddedActors);
    }

    // POST api/movies/5/watch
    @RequestMapping(value = "{id}/watch", method = RequestMethod.POST)
    public ResponseEntity addMovieToWatchlist(@PathVariable Long id, @CurrentUser UserPrincipal currentUser) {
        Movie movie = movieService.getMovieById(id);
        User user = userService.getUserById(currentUser.getId());
        movieService.addMovieToWatchlist(movie, user);
        return ResponseEntity.ok("{}");
    }

    // DELETE /api/movies/5
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMovie(@PathVariable Long id) {
        try {
            movieService.deleteMovieById(id);
            return ResponseEntity.ok("{}");
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("movie", "id", id.toString());
        }
    }

    // DELETE /api/movies/5/watch
    @RequestMapping(value = "{id}/watch", method = RequestMethod.DELETE)
    public ResponseEntity removeMovieFromWatchlist(
            @PathVariable Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        Movie movie = movieService.getMovieById(id);
        User user = userService.getUserById(currentUser.getId());
        movieService.removeMovieFromWatchlist(movie, user);
        return ResponseEntity.ok("{}");
    }

}
