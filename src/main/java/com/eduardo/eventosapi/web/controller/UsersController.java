package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.services.RegistrationService;
import com.eduardo.eventosapi.services.UsersService;
import com.eduardo.eventosapi.web.dtos.mapper.RegistrationMapper;
import com.eduardo.eventosapi.web.dtos.request.RegistrationRequestDTO;
import com.eduardo.eventosapi.web.dtos.request.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.RegistrationResponseDTO;
import com.eduardo.eventosapi.web.dtos.response.UsersResponseDTO;
import com.eduardo.eventosapi.web.dtos.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/users")
@Tag(name = "users", description = "API para gerenciamento de usuários")
public class UsersController {

    private final UsersService service;
    private final RegistrationService registrationService;

    @Operation(summary = "Cria um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })

    @PostMapping
    public ResponseEntity<UsersResponseDTO> create(@Valid @RequestBody UsersRequestDTO dto) {
        User createUser = service.create(UsersMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsersMapper.toDto(createUser));
    }

    @Operation(summary = "Busca um usuário pelo ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.")
    })

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> findById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok(UsersMapper.toDto(user));
    }

    @Operation(summary = "Atualiza dados do usuário", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuario atualizado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal fortamados.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UsersRequestDTO dto) {
        User updateUser = service.update(id, dto);
        return ResponseEntity.ok(UsersMapper.toDto(updateUser));
    }

    @Operation(summary = "Listar todos os usuários", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todos os usuarios cadastrados."),
    })
    @GetMapping
    public ResponseEntity<Page<UsersResponseDTO>> getAll(@RequestParam("page") int page, @RequestParam("itens") int itens ) {
        Pageable pageable = PageRequest.of(page, itens);
        Page<User> usersPage = service.findAll(page, itens);
        return ResponseEntity.ok(usersPage.map(UsersMapper::toDto));
    }


    @Operation(summary = "Deletar usuario por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Registrar usuário para evento",method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário ou evento não encontrado."),
            @ApiResponse(responseCode = "409", description = "Usuário já registrado para o evento.")
    })
    @PostMapping("/{userId}/register/{eventId}")
    public ResponseEntity<Void> registerUserForEvent(@RequestBody RegistrationRequestDTO registrationRequestDTO) {
        registrationService.registerUserForEvent(registrationRequestDTO.getUserId(), registrationRequestDTO.getEventId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Obter registros de eventos por usuário",method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registros de eventos obtidos com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })

    @GetMapping("/{userId}/event-registrations")
    public ResponseEntity<List<RegistrationResponseDTO>> getRegistrationsByUser(@PathVariable Long userId) {
        List<Registration> registrations = registrationService.getRegistrationsByUserId(userId);
        List<RegistrationResponseDTO> responseDTOList = RegistrationMapper.toListDto(registrations);
        return ResponseEntity.ok(responseDTOList);
    }
}
