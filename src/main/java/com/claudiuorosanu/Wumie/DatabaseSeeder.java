package com.claudiuorosanu.Wumie;

import com.claudiuorosanu.Wumie.model.*;
import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import com.claudiuorosanu.Wumie.model.enums.RoleName;
import com.claudiuorosanu.Wumie.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final ActorMovieRepository actorMovieRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public DatabaseSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            MovieRepository movieRepository,
            ActorRepository actorRepository,
            ActorMovieRepository actorMovieRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.actorMovieRepository = actorMovieRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        seedUsersTable();
        seedMoviesTable();
        seedActorsTable();
        seedActorMovieTable();
    }

    private void seedUsersTable() {
        if (userRepository.existsByUsername("admin")) {
            // data already exists, exit
            log.info("No need to seed users and roles table. Data already there");
            return;
        }

        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        Role userRole = new Role(RoleName.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(adminRole, userRole));

        User adminUser = new User(
                "Admin",
                "Global",
                "admin",
                "admin@gmail.com",
                passwordEncoder.encode("adminadmin")
        );
        adminUser.getRoles().add(adminRole);

        User normalUser = new User(
                "Claudiu",
                "Orosanu",
                "claudiu",
                "claudiu@gmail.com",
                passwordEncoder.encode("claudiu")
        );
        normalUser.getRoles().add(userRole);

        userRepository.saveAll(Arrays.asList(adminUser, normalUser));
    }

    private void seedMoviesTable() {

        if (movieRepository.count() > 0) {
            log.info("No need to seed movies table. Data already there");
            return;
        }

        List<Movie> movies = Arrays.asList(
                new Movie(
                        "Blade Runner 2049",
                        LocalDate.of(2017, 10, 6),
                        164,
                        Genre.ScienceFiction,
                        Language.English,
                        "A young blade runner's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard",
                        BigDecimal.valueOf(91800042)
                ),
                new Movie(
                        "Dunkirk",
                        LocalDate.of(2017, 7, 21),
                        106,
                        Genre.Drama,
                        Language.English,
                        "Allied soldiers from Belgium, the British Empire and France are surrounded by the German Army, and evacuated during a fierce battle in World War II.",
                        BigDecimal.valueOf(188042171)
                ),
                new Movie(
                        "Wonder",
                        LocalDate.of(2017, 11, 17),
                        113,
                        Genre.Drama,
                        Language.English,
                        "Based on the New York Times bestseller, WONDER tells the incredibly inspiring and heartwarming story of August Pullman, a boy with facial differences who enters fifth grade, attending a mainstream elementary school for the first time.",
                        BigDecimal.valueOf(126638742)
                ),
                new Movie(
                        "Drive",
                        LocalDate.of(2011, 7, 16),
                        100,
                        Genre.Action,
                        Language.English,
                        "A mysterious Hollywood stuntman and mechanic moonlights as a getaway driver and finds himself in trouble when he helps out his neighbor.",
                        BigDecimal.valueOf(3506000)
                ),
                new Movie(
                        "Geostorm",
                        LocalDate.of(2017, 9, 20),
                        109,
                        Genre.Drama,
                        Language.English,
                        "When the network of satellites designed to control the global climate starts to attack Earth, it's a race against the clock for its creator to uncover the real threat before a worldwide Geostorm wipes out everything and everyone.",
                        BigDecimal.valueOf(33700000)
                )
        );
        movieRepository.saveAll(movies);
    }

    private void seedActorsTable() {
        if (actorRepository.count() > 0) {
            log.info("No need to seed actors table. Data already there");
            return;
        }

        List<Actor> actors = Arrays.asList(
                new Actor(
                        "Ryan",
                        "Gosling",
                        LocalDate.of(1980, 11, 12)
                ),
                new Actor(
                        "Gerard",
                        "Butler",
                        LocalDate.of(1969, 11, 13)
                ),
                new Actor(
                        "Ed",
                        "Harris",
                        LocalDate.of(1950, 11, 28)
                ),
                new Actor(
                        "Fionn",
                        "Whitehead",
                        LocalDate.of(1992, 4, 2)
                ),
                new Actor(
                        "Owen",
                        "Wilson",
                        LocalDate.of(1968, 11, 18)
                )
        );
        actorRepository.saveAll(actors);
    }

    private void seedActorMovieTable() {
        if (actorMovieRepository.count() > 0) {
            log.info("No need to seed actor_movie table. Data already there");
            return;
        }

        Actor ryanGosling = actorRepository.findByLastNameAndFirstName("Gosling", "Ryan").get();
        Actor gerardButler = actorRepository.findByLastNameAndFirstName("Butler", "Gerard").get();
        Actor edHarris = actorRepository.findByLastNameAndFirstName("Harris", "Ed").get();
        Actor fionnWhitehead = actorRepository.findByLastNameAndFirstName("Whitehead", "Fionn").get();
        Actor owenWilson = actorRepository.findByLastNameAndFirstName("Wilson", "Owen").get();

        Movie bd2049 = movieRepository.findByTitle("Blade Runner 2049").get();
        Movie drive = movieRepository.findByTitle("Drive").get();
        Movie geostorm = movieRepository.findByTitle("Geostorm").get();
        Movie dunkirk = movieRepository.findByTitle("Dunkirk").get();
        Movie wonder = movieRepository.findByTitle("Wonder").get();

        List<ActorMovie> actorMovies = Arrays.asList(
                new ActorMovie(bd2049, ryanGosling),
                new ActorMovie(drive, ryanGosling),
                new ActorMovie(geostorm, gerardButler),
                new ActorMovie(geostorm, edHarris),
                new ActorMovie(dunkirk, fionnWhitehead),
                new ActorMovie(wonder, owenWilson)
        );

        actorMovieRepository.saveAll(actorMovies);
    }

}

