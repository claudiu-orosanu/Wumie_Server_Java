package com.claudiuorosanu.Wumie.model;

import com.claudiuorosanu.Wumie.model.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Actor extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

//    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    public Actor(
            String firstName,
            String lastName,
            LocalDate birthDate
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // relationships
    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    private Set<ActorMovie> actorMovies = new HashSet<>();

}
