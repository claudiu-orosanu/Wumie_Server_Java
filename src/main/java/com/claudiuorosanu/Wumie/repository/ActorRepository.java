package com.claudiuorosanu.Wumie.repository;

import com.claudiuorosanu.Wumie.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByFirstName(String firstName);
    Optional<Actor> findByLastName(String lastName);
    Optional<Actor> findByLastNameAndFirstName(String lastName, String firstName);
}
