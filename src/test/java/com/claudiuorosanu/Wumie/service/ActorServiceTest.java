package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.model.Actor;
import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import com.claudiuorosanu.Wumie.repository.ActorRepository;
import com.claudiuorosanu.Wumie.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ActorServiceTest {

    @Autowired
    private ActorService actorService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Before
    public void setUp() throws Exception {
        movieRepository.deleteAll();
        actorRepository.deleteAll();
    }

    private void insertMovies() {
        movieRepository.saveAll(Arrays.asList(
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
                )
            )
        );
    }

    private void insertActors() {
        actorRepository.saveAll(Arrays.asList(
                new Actor(
                        "Ryan",
                        "Gosling",
                        LocalDate.of(1980, 11, 12)
                ),
                new Actor(
                        "Gerard",
                        "Butler",
                        LocalDate.of(1969, 11, 13)
                )
        ));
    }

    @Test
    public void test_getAllActors() {
        List<Actor> allActors = actorService.getAllActors();
        assertEquals(0, allActors.size());

        insertActors();

        allActors = actorService.getAllActors();
        assertEquals(2, allActors.size());
        assertEquals("Ryan", allActors.get(0).getFirstName());
        assertEquals("Gerard", allActors.get(1).getFirstName());
    }

    @Test
    public void test_getActorById() {
        insertActors();

        Long id = actorService.getAllActors().get(0).getId();
        Actor actor = actorService.getActorById(id);
        assertEquals(id, actor.getId());
        assertEquals("Ryan", actor.getFirstName());
    }

    @Test
    public void test_createActor() {
        ActorDto actorDto = new ActorDto(1L, "Claudiu", "Orosanu", LocalDate.now());
        Actor createdActor = actorService.createActor(actorDto);

        assertEquals(actorDto.getFirstName(), createdActor.getFirstName());
        assertEquals(actorDto.getLastName(), createdActor.getLastName());
        assertEquals(actorDto.getBirthDate(), createdActor.getBirthDate());
    }

    @Test
    public void test_updateActor() {
        insertActors();

        Actor actor = actorRepository.findByFirstName("Ryan").get();
        actor.setLastName("Gigi");
        actorService.updateActor(actor);

        actor = actorRepository.findByFirstName("Ryan").get();
        assertEquals("Gigi", actor.getLastName());
    }

    @Test
    @Transactional
    public void test_getActorsForMovie() {
        insertMovies();
        insertActors();

        // add all actors to blade runner movie
        Movie bladeRunnerMovie = movieRepository.findByTitle("Blade Runner 2049").get();
        List<Long> actorsIds = actorRepository.findAll().stream().map(Actor::getId).collect(Collectors.toList());
        movieService.addActorsToMovie(bladeRunnerMovie, actorsIds);

        // get actors from blade runner movie
        bladeRunnerMovie = movieRepository.findByTitle("Blade Runner 2049").get();
        List<Actor> actorsForMovie = actorService.getActorsForMovie(bladeRunnerMovie);

        assertEquals(2, actorsForMovie.size());
    }

    @Test
    public void test_deleteActorById() {
        insertActors();
        Actor actor = actorRepository.findByFirstName("Ryan").get();
        actorService.deleteActorById(actor.getId());

        List<Actor> remainingActors = actorRepository.findAll();
        assertEquals(1, remainingActors.size());
        assertEquals("Gerard", remainingActors.get(0).getFirstName());
    }
}
