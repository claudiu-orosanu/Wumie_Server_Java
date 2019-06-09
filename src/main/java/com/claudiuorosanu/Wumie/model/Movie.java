package com.claudiuorosanu.Wumie.model;

import com.claudiuorosanu.Wumie.model.audit.DateAudit;
import com.claudiuorosanu.Wumie.model.enums.Genre;
import com.claudiuorosanu.Wumie.model.enums.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

//    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;

    @Max(500)
    private Integer runtime;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Language language;

//    @Max(10000)
    private String plot;

    private BigDecimal boxOffice;

    public Movie(
            String title,
            LocalDate releaseDate,
            Integer runtime,
            Genre genre,
            Language language,
            String plot,
            BigDecimal boxOffice
    ) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.genre = genre;
        this.language = language;
        this.plot = plot;
        this.boxOffice = boxOffice;
    }

    // relationships
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<ActorMovie> actorMovie = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<AwardMovie> awardMovies = new HashSet<>();

    @ManyToMany(mappedBy = "watchlist", cascade = CascadeType.ALL)
    private Set<User> usersToWatch = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();

}
