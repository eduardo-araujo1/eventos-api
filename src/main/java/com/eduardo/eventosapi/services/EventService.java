package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepostirory repostirory;

    @Transactional
    public Event save(Event event) {
        return repostirory.save(event);
    }

    @Transactional(readOnly = true)
    public Event findById(Long id){
        return repostirory.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Evento id=%s não encontrado", id)));
    }
}
