package com.claudiuorosanu.Wumie.repository;

import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);
}
