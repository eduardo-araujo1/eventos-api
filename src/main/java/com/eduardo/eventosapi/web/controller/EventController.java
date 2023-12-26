package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.services.EventService;
import com.eduardo.eventosapi.web.dtos.EventRequestDTO;
import com.eduardo.eventosapi.web.dtos.EventResponseDTO;
import com.eduardo.eventosapi.web.dtos.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    @PostMapping
    public ResponseEntity<EventResponseDTO> save(@RequestBody EventRequestDTO dto) {
        Event createdEvent = service.save(EventMapper.toEvent(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(EventMapper.toDto(createdEvent));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findById(@PathVariable Long id){
        Event event = service.findById(id);
        return ResponseEntity.ok(EventMapper.toDto(event));
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> findAll(){
        List<Event> eventList = service.findAll();
        return ResponseEntity.ok(EventMapper.toListDto(eventList));
    }
}
