package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.Movie;

import java.util.List;

public interface ActorService {
    List<Actor> getActorsForMovie(Movie movie);

    List<Actor> getAllActors();

    Actor getActorById(Long id);

    void updateActor(Actor actor);

    Actor createActor(ActorDto actorDto);

    void deleteActorById(Long id);
}
