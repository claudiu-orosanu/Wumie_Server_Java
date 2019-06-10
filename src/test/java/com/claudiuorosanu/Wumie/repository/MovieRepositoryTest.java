package com.claudiuorosanu.Wumie.repository;

import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Before
    public void setUp() throws Exception {
        movieRepository.deleteAll();
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
            )
        );
    }

    @Test
    public void test_saveMovie() {
        Movie createdMovie = movieRepository.save(new Movie(
                "Blade Runner 2049",
                LocalDate.of(2017, 10, 6),
                164,
                Genre.ScienceFiction,
                Language.English,
                "A young blade runner's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard",
                BigDecimal.valueOf(91800042)
        ));

        assertNotNull(createdMovie.getId());
        assertEquals("Blade Runner 2049", createdMovie.getTitle());
        assertEquals(LocalDate.of(2017, 10, 6), createdMovie.getReleaseDate());
        assertEquals(Integer.valueOf(164), createdMovie.getRuntime());
        assertEquals(Genre.ScienceFiction, createdMovie.getGenre());
        assertEquals(Language.English, createdMovie.getLanguage());
        assertEquals(
                "A young blade runner's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard",
                createdMovie.getPlot()
        );
        assertEquals(BigDecimal.valueOf(91800042), createdMovie.getBoxOffice());
    }

    @Test
    public void test_findAllMovies() {
        insertMovies();
        assertEquals(5, movieRepository.findAll().size());
    }

    @Test
    public void test_findByTitle() {
        insertMovies();
        Movie dunkirk = movieRepository.findByTitle("Dunkirk").get();
        assertEquals("Dunkirk", dunkirk.getTitle());
        assertEquals((Integer) 106, dunkirk.getRuntime());
    }
}
