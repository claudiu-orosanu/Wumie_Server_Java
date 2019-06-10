package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.Movie;

import java.util.List;

public interface ActorService {
    List<Actor> getActorsForMovie(Movie movie);
}
