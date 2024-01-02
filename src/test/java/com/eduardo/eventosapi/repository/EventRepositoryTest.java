package com.eduardo.eventosapi.repository;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testFindAll() {
        Event event1 = new Event(1L, "Event1", "Description1", "Location1", LocalDate.now());
        Event event2 = new Event(2L, "Event2", "Description2", "Location2", LocalDate.now());
        eventRepository.saveAll(Arrays.asList(event1, event2));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> resultPage = eventRepository.findAll(pageable);

        assertThat(resultPage.getTotalElements()).isEqualTo(2);
        assertThat(resultPage.getContent()).containsExactly(event1, event2);
    }

}
