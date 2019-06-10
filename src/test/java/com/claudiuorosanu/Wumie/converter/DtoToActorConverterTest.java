package com.claudiuorosanu.Wumie.converter;

import com.claudiuorosanu.Wumie.converters.DtoToActorConverter;
import com.claudiuorosanu.Wumie.dto.ActorDto;
import com.claudiuorosanu.Wumie.model.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DtoToActorConverterTest {
    
    @Autowired
    private DtoToActorConverter dtoToActorConverter;

    @Test
    public void convertNull() {
        assertNull(dtoToActorConverter.convert(null));
    }

    @Test
    public void convert() {
        ActorDto actorDto = new ActorDto(1L, "Gigi", "Gigescu", LocalDate.now());

        Actor actor = dtoToActorConverter.convert(actorDto);

        assertNotNull(actorDto);
        assertEquals(actorDto.getFirstName(), actor.getFirstName());
        assertEquals(actorDto.getLastName(), actor.getLastName());
        assertEquals(actorDto.getBirthDate(), actor.getBirthDate());

    }
}
