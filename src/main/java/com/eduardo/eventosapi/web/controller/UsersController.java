package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.services.UsersService;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.UsersResponseDTO;
import com.eduardo.eventosapi.web.dtos.mapper.UsersMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Target;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "users")
public class UsersController {

    private final UsersService service;

    @Operation(summary = "Cria um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })

    @PostMapping
    public ResponseEntity<UsersResponseDTO> create (@Valid @RequestBody UsersRequestDTO dto){
       Users createUser = service.create(UsersMapper.toUser(dto));
       return ResponseEntity.status(HttpStatus.CREATED).body(UsersMapper.toDto(createUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> findById (@PathVariable Long id){
        Users users = service.findById(id);
        return ResponseEntity.ok(UsersMapper.toDto(users));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UsersRequestDTO dto){
        Users updateUser = service.update(id, dto);
        return ResponseEntity.ok(UsersMapper.toDto(updateUser));
    }

    @GetMapping
    public ResponseEntity<List<UsersResponseDTO>> getAll(){
        List<Users> users = service.findAll();
        return ResponseEntity.ok(UsersMapper.toListDto(users));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
