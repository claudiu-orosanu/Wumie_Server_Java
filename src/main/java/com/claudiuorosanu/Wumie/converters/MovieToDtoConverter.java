package com.claudiuorosanu.Wumie.converters;

import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieToDtoConverter implements Converter<Movie, MovieDto> {

    @Override
    public MovieDto convert(Movie movie) {
        if (Objects.isNull(movie)) {
            return null;
        }

        return new MovieDto(
            movie.getId(),
            movie.getTitle(),
            movie.getReleaseDate(),
            movie.getRuntime(),
            movie.getGenre(),
            movie.getLanguage(),
            movie.getPlot(),
            movie.getBoxOffice()
        );
    }
}
