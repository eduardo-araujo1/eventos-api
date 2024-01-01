package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.services.EventService;
import com.eduardo.eventosapi.services.RegistrationService;
import com.eduardo.eventosapi.web.dtos.mapper.EventMapper;
import com.eduardo.eventosapi.web.dtos.mapper.RegistrationMapper;
import com.eduardo.eventosapi.web.dtos.request.EventRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.EventResponseDTO;
import com.eduardo.eventosapi.web.dtos.response.RegistrationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Tag(name = "events", description = "API para gerenciamento de eventos")
public class EventController {

    private final EventService service;
    private final RegistrationService registrationService;


    @Operation(summary = "Cria um novo evento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.")
    })
    @PostMapping
    public ResponseEntity<EventResponseDTO> save(@RequestBody EventRequestDTO dto) {
        Event createdEvent = service.save(EventMapper.toEvent(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(EventMapper.toDto(createdEvent));
    }

    @Operation(summary = "Busca um evento pelo ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findById(@PathVariable Long id){
        Event event = service.findById(id);
        return ResponseEntity.ok(EventMapper.toDto(event));
    }

    @Operation(summary = "Listar todos os eventos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todos os eventos cadastrados."),
    })
    @GetMapping
    public ResponseEntity<Page<EventResponseDTO>> findAll(@RequestParam("page") int page, @RequestParam("itens") int itens){
        Pageable pageable = PageRequest.of(page, itens);
        Page<Event> eventsPage = service.findAll(page, itens);
        return ResponseEntity.ok(eventsPage.map(EventMapper::toDto));
    }

    @Operation(summary = "Obter registros de usuários para um evento", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de usuários obtido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado.")
    })

    @GetMapping("/{eventId}/user-registrations")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationsByEventId(@PathVariable Long eventId){
        List<Registration> registrations = registrationService.getRegistrationsByEventId(eventId);
        List<RegistrationResponseDTO> responseDTOList = RegistrationMapper.toListDto(registrations);
        return ResponseEntity.ok(responseDTOList);
    }
}
