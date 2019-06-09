package com.claudiuorosanu.Wumie.repository;

import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.ActorMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorMovieRepository extends JpaRepository<ActorMovie, Long> {

    Optional<ActorMovie> findByActor_IdAndMovie_Id(Long actorId, Long movieId);

}
