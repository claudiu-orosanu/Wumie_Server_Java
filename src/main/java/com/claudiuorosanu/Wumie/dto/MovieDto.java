package com.claudiuorosanu.Wumie.dto;

import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class MovieDto {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    private LocalDate releaseDate;

    @Max(500)
    private Integer runtime;

    private Genre genre;

    private Language language;

    //    @Max(10000)
    private String plot;

    private BigDecimal boxOffice;

    private int actorsCount;
    private boolean isWatched;

    public MovieDto(
            Long id,
            String title,
            LocalDate releaseDate,
            Integer runtime,
            Genre genre,
            Language language,
            String plot,
            BigDecimal boxOffice
    ) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.genre = genre;
        this.language = language;
        this.plot = plot;
        this.boxOffice = boxOffice;
    }
}
