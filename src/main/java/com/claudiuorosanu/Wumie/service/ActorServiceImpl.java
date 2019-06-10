package com.claudiuorosanu.Wumie.service;

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

    @Override
    public List<Actor> getActorsForMovie(Movie movie) {
        Set<ActorMovie> actorMovies = movie.getActorMovies();

        return actorMovies.stream()
                          .map(ActorMovie::getActor)
                          .collect(Collectors.toList());
    }
}
