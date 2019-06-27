package com.claudiuorosanu.Wumie.converter;

import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.model.Actor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DtoToActorConverter implements Converter<ActorDto, Actor> {

    @Override
    public Actor convert(ActorDto actorDto) {
        if (Objects.isNull(actorDto)) {
            return null;
        }

        return new Actor(
            actorDto.getFirstName(),
            actorDto.getLastName(),
            actorDto.getBirthDate()
        );
    }
}
