package com.claudiuorosanu.Wumie.model;

import com.claudiuorosanu.Wumie.model.audit.DateAudit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Award extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    // relationships
    @OneToMany(mappedBy = "award", cascade = CascadeType.ALL)
    private Set<AwardMovie> awardMovies = new HashSet<>();
    
}
