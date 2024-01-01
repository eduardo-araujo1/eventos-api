package com.eduardo.eventosapi.service;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import com.eduardo.eventosapi.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    public static final Event EVENT = new Event(1L, "Event Name", "Description", "Location", LocalDate.now().plusDays(7));
    public static final Event INVALID_EVENT = new Event(2L, "", "", "", null);

    public static final Event EVENT1 = new Event(3L, "Birthday Party", "Come join the celebration", "Party Hall", LocalDate.now().plusDays(9));
    public static final Event EVENT2 = new Event(4L, "Football", "Come play football", "Morumbi Stadium", LocalDate.now().plusMonths(2));

    public static final List<Event> EVENT_LIST = Arrays.asList(EVENT1, EVENT2);

    @Mock
    private EventRepostirory repostirory;

    @InjectMocks
    private EventService eventService;

    @Test
    public void saveEvent_WithValidData_ReturnsEvent() {
        when(repostirory.save(EVENT)).thenReturn(EVENT);

        Event savedEvent = eventService.save(EVENT);

        assertThat(savedEvent).isEqualTo(EVENT);
    }

    @Test
    public void saveEvent_WithInvalidData_ThrowsException() {
        when(repostirory.save(INVALID_EVENT)).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> eventService.save(INVALID_EVENT))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void findEvent_ByExistingId_ReturnsEvent() {
        when(repostirory.findById(3L)).thenReturn(Optional.of(EVENT1));

        Event foundEvent = eventService.findById(3L);

        assertThat(foundEvent).isNotNull().isEqualTo(EVENT1);
    }

    @Test
    public void findEvent_ByNonExistingId_ThrowsException() {
        when(repostirory.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.findById(10L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void findAllEvents_ReturnsListOfEvents() {
        Page<Event> eventPage = new PageImpl<>(EVENT_LIST);
        when(repostirory.findAll(PageRequest.of(0, 10))).thenReturn(eventPage);

        // Chamando o método do serviço
        Page<Event> events = eventService.findAll(0, 10);

        // Verificando as condições de teste
        assertThat(events.getContent()).isNotNull().hasSize(2);
    }

    @Test
    public void findAllEvents_ReturnsEmptyList() {
        // Mockando o método findAll do repositório para retornar uma Page vazia
        Page<Event> emptyEventPage = new PageImpl<>(Collections.emptyList());
        when(repostirory.findAll((Pageable) any())).thenReturn(emptyEventPage);

        // Chamando o método do serviço
        Page<Event> events = eventService.findAll(0,10);

        // Verificando as condições de teste
        assertThat(events.getContent()).isNotNull().isEmpty();
    }
}

