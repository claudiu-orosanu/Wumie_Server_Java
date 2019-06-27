package com.claudiuorosanu.Wumie.controller;


import com.claudiuorosanu.Wumie.converter.ActorToDtoConverter;
import com.claudiuorosanu.Wumie.converter.MovieToDtoConverter;
import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.service.ActorService;
import com.claudiuorosanu.Wumie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private ActorToDtoConverter actorToDtoConverter;
    @Autowired
    private MovieToDtoConverter movieToDtoConverter;

    @GetMapping
    // GET /api/actors
    public List<ActorDto> getActors() {

        List<Actor> actors = actorService.getAllActors();

        return actors
            .stream()
            .map(actor -> {
                int moviesCount = actor.getActorMovies().size();
                ActorDto actorDto = actorToDtoConverter.convert(actor);
                actorDto.setMoviesCount(moviesCount);
                return actorDto;
            })
            .collect(Collectors.toList());
    }

    // GET /api/actor/5
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ActorDto getActor(@PathVariable Long id) {
        Actor actor = actorService.getActorById(id);
        return actorToDtoConverter.convert(actor);
    }

    // GET /api/actor/5/movies
    @RequestMapping(value = "{id}/movies", method = RequestMethod.GET)
    public List<MovieDto> getMoviesForActor(@PathVariable Long id) {
        Actor actor = actorService.getActorById(id);
        List<Movie> movies = movieService.getMoviesForActor(actor);

        return movies.stream()
                     .map(movieToDtoConverter::convert)
                     .collect(Collectors.toList());
    }

    // PUT /api/actors/5
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateActor(@PathVariable Long id, @Valid @RequestBody Actor actor) {
        actor.setId(id);
        actorService.updateActor(actor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // POST /api/actors
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createActor(@Valid @RequestBody ActorDto actorDto) {
        Actor createdActor = actorService.createActor(actorDto);
        ActorDto createdActorDto = actorToDtoConverter.convert(createdActor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{actorId}")
                .buildAndExpand(createdActorDto.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdActorDto);
    }

    // DELETE /api/actors/5
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteActor(@PathVariable Long id) {
        actorService.deleteActorById(id);
    }

}
