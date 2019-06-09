package com.claudiuorosanu.Wumie.model;

import com.claudiuorosanu.Wumie.model.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ActorMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relationships
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

    public ActorMovie(Movie movie, Actor actor) {
        this.movie = movie;
        this.actor = actor;
    }

    //    private String characterName;
//    private Integer minutesPlayed;
//    private Integer numberOfSayings;

}
