package com.claudiuorosanu.Wumie.converter;

import com.claudiuorosanu.Wumie.dto.MovieDto;
import com.claudiuorosanu.Wumie.model.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DtoToMovieConverter implements Converter<MovieDto, Movie> {

    @Override
    public Movie convert(MovieDto movieDto) {
        if (Objects.isNull(movieDto)) {
            return null;
        }

        return new Movie(
            movieDto.getTitle(),
            movieDto.getReleaseDate(),
            movieDto.getRuntime(),
            movieDto.getGenre(),
            movieDto.getLanguage(),
            movieDto.getPlot(),
            movieDto.getBoxOffice()
        );
    }
}
