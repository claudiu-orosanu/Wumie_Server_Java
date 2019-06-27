package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.converter.DtoToActorConverter;
import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.exception.ResourceNotFoundException;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.ActorMovie;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DtoToActorConverter dtoToActorConverter;

    @Override
    public List<Actor> getActorsForMovie(Movie movie) {
        Set<ActorMovie> actorMovies = movie.getActorMovies();

        return actorMovies.stream()
                          .map(ActorMovie::getActor)
                          .collect(Collectors.toList());
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor getActorById(Long id) {

        Actor actor = actorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("actor", "id", id.toString()));

        return actor;
    }

    @Override
    public void updateActor(Actor actor) {
        actorRepository.save(actor);
    }

    @Override
    public Actor createActor(ActorDto actorDto) {
        Actor actor = dtoToActorConverter.convert(actorDto);
        return actorRepository.save(actor);
    }

    @Override
    public void deleteActorById(Long id) {
        actorRepository.deleteById(id);
    }
}
