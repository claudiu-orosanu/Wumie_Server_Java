package com.claudiuorosanu.Wumie.converter;

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
public class ActorToDtoConverterTest {
    
    @Autowired
    private ActorToDtoConverter actorToDtoConverter;
    
    @Test
    public void convertNull() {
        assertNull(actorToDtoConverter.convert(null));
    }

    @Test
    public void convert() {
        Actor actor = new Actor("Gigi", "Gigescu", LocalDate.now());
        actor.setId(1L);

        ActorDto actorDto = actorToDtoConverter.convert(actor);

        assertNotNull(actorDto);
        assertEquals(actor.getId(), actorDto.getId());
        assertEquals(actor.getFirstName(), actorDto.getFirstName());
        assertEquals(actor.getLastName(), actorDto.getLastName());
        assertEquals(actor.getBirthDate(), actorDto.getBirthDate());

    }
}
