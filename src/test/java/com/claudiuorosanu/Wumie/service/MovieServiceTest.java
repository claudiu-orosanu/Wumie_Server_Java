package com.claudiuorosanu.Wumie.service;

import com.claudiuorosanu.Wumie.dto.MovieDto;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MovieServiceTest {

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
    public void test_getAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();
        assertEquals(0, allMovies.size());

        insertMovies();

        List<Movie> movies = movieService.getAllMovies();
        assertEquals(2, movies.size());
        assertEquals("Blade Runner 2049", movies.get(0).getTitle());
        assertThat(movies.get(1).getTitle(), is("Dunkirk"));
    }

    @Test
    public void test_getMovieById() {
        insertMovies();

        Long id = movieService.getAllMovies().get(0).getId();
        Movie movie = movieService.getMovieById(id);
        assertEquals(id, movie.getId());
        assertEquals("Blade Runner 2049", movie.getTitle());
    }

    @Test
    public void test_createMovie() {
        MovieDto movieDto = new MovieDto(
                1L,
                "fake title",
                LocalDate.now(),
                120,
                Genre.Drama,
                Language.French,
                "simple plot",
                BigDecimal.valueOf(22)
        );
        Movie createdMovie = movieService.createMovie(movieDto);

        assertEquals(movieDto.getTitle(), createdMovie.getTitle());
        assertEquals(movieDto.getReleaseDate(), createdMovie.getReleaseDate());
        assertEquals(movieDto.getRuntime(), createdMovie.getRuntime());
        assertEquals(movieDto.getGenre(), createdMovie.getGenre());
        assertEquals(movieDto.getLanguage(), createdMovie.getLanguage());
        assertEquals(movieDto.getPlot(), createdMovie.getPlot());
        assertEquals(movieDto.getBoxOffice(), createdMovie.getBoxOffice());
    }

    @Test
    public void test_updateMovie() {
        insertMovies();

        Movie movie = movieService.getAllMovies().get(0);
        movie.setPlot("blabla");
        movieService.updateMovie(movie);

        movie = movieService.getAllMovies().get(0);
        assertEquals("blabla", movie.getPlot());
    }

    @Test
    @Transactional
    public void test_addActorsToMovie() {
        insertMovies();
        insertActors();
        Movie bladeRunnerMovie = movieRepository.findByTitle("Blade Runner 2049").get();
        List<Long> actorsIds = actorRepository.findAll().stream().map(Actor::getId).collect(Collectors.toList());
        movieService.addActorsToMovie(bladeRunnerMovie, actorsIds);

        bladeRunnerMovie = movieRepository.findByTitle("Blade Runner 2049").get();
        assertEquals(2, bladeRunnerMovie.getActorMovies().size());
    }

    @Test
    public void test_deleteMovieById() {
        insertMovies();
        Movie bladeRunnerMovie = movieRepository.findByTitle("Blade Runner 2049").get();
        movieService.deleteMovieById(bladeRunnerMovie.getId());

        List<Movie> remainingMovies = movieRepository.findAll();
        assertEquals(1, remainingMovies.size());
        assertEquals("Dunkirk", remainingMovies.get(0).getTitle());
    }
}
