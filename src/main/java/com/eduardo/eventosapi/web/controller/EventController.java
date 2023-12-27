package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.services.EventService;
import com.eduardo.eventosapi.services.RegistrationService;
import com.eduardo.eventosapi.web.dtos.mapper.RegistrationMapper;
import com.eduardo.eventosapi.web.dtos.request.EventRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.EventResponseDTO;
import com.eduardo.eventosapi.web.dtos.mapper.EventMapper;
import com.eduardo.eventosapi.web.dtos.response.RegistrationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService service;
    private final RegistrationService registrationService;

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

    @GetMapping("/{eventId}/user-registrations")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationsByEventId(@PathVariable Long eventId){
        List<Registration> registrations = registrationService.getRegistrationsByEventId(eventId);
        List<RegistrationResponseDTO> responseDTOList = RegistrationMapper.toListDto(registrations);
        return ResponseEntity.ok(responseDTOList);
    }
}
