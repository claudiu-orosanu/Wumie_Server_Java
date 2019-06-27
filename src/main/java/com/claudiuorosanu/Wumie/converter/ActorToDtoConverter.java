package com.claudiuorosanu.Wumie.converter;

import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.model.Actor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ActorToDtoConverter implements Converter<Actor, ActorDto> {

    @Override
    public ActorDto convert(Actor actor) {
        if (Objects.isNull(actor)) {
            return null;
        }

        return new ActorDto(
            actor.getId(),
            actor.getFirstName(),
            actor.getLastName(),
            actor.getBirthDate()
        );
    }
}
