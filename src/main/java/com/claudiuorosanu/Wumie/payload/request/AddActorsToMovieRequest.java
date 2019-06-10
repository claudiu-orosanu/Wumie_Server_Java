package com.claudiuorosanu.Wumie.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class AddActorsToMovieRequest {

    @NotBlank
    private List<Long> actorIds;
}
