package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public Page<Event> findAll(int page, int itens){
        return repostirory.findAll(PageRequest.of(page, itens));
    }
}
