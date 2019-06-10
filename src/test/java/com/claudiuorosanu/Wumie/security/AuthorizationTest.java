package com.claudiuorosanu.Wumie.security;

import com.claudiuorosanu.Wumie.model.Movie;
import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import com.claudiuorosanu.Wumie.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthorizationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MovieRepository movieRepository;

    @Getter
    @Setter
    private static class JWTResponse {
        private String accessToken;
        private String tokenType;
    }

    @Test
    public void test_user_cannot_delete_movie() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "\t\"usernameOrEmail\": \"claudiu\",\n" +
                                "\t\"password\": \"claudiu\"\n" +
                                "}"
                );

        mvc.perform(loginRequest).andExpect(
                mvcResult -> {
                    String content = mvcResult.getResponse().getContentAsString();
                    JWTResponse jwtResponse = objectMapper.readValue(content, JWTResponse.class);
                    String accessToken = jwtResponse.getAccessToken();
                    assertNotNull(accessToken);

                    MockHttpServletRequestBuilder moviesRequest = MockMvcRequestBuilders
                            .delete("/api/movies/1")
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + accessToken);

                    mvc.perform(moviesRequest).andExpect(mvcResult1 -> {
                        assertEquals(403, mvcResult1.getResponse().getStatus());
                        assertEquals("Forbidden", mvcResult1.getResponse().getErrorMessage());
                    });
                }
        );
    }


    @Test
    public void test_admin_can_delete_movie() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder loginRequest = MockMvcRequestBuilders
                .post("/api/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "\t\"usernameOrEmail\": \"admin\",\n" +
                                "\t\"password\": \"adminadmin\"\n" +
                                "}"
                );


        // create movie to be deleted
        Movie movie = movieRepository.save(new Movie(
                   "Dark night",
                   LocalDate.of(2017, 10, 6),
                   123,
                   Genre.ScienceFiction,
                   Language.English,
                   "No plot",
                   BigDecimal.valueOf(4)
           )
        );

        mvc.perform(loginRequest).andExpect(
                mvcResult -> {
                    String content = mvcResult.getResponse().getContentAsString();
                    JWTResponse jwtResponse = objectMapper.readValue(content, JWTResponse.class);
                    String accessToken = jwtResponse.getAccessToken();
                    assertNotNull(accessToken);

                    MockHttpServletRequestBuilder moviesRequest = MockMvcRequestBuilders
                            .delete("/api/movies/" + movie.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + accessToken);

                    mvc.perform(moviesRequest).andExpect(mvcResult1 -> {
                        assertEquals(200, mvcResult1.getResponse().getStatus());
                    });
                }
        );
    }

}
