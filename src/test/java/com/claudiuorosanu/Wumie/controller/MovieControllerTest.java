package com.claudiuorosanu.Wumie.controller;

import com.claudiuorosanu.Wumie.security.JwtTokenProvider;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @Getter
    @Setter
    private static class JWTResponse {
        private String accessToken;
        private String tokenType;
    }

    @Test
    public void test_getMovies() throws Exception {

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
                            .get("/api/movies")
                            .accept(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + accessToken);

                    mvc.perform(moviesRequest)
                       .andExpect(status().isOk())
                       .andExpect(content().string(containsString("Blade Runner 2049")));
                }
        );
    }

}
