package com.eduardo.eventosapi.web.controller;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.services.UsersService;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.UsersResponseDTO;
import com.eduardo.eventosapi.web.dtos.mapper.UsersMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UsersController {

    private final UsersService service;

    @PostMapping
    public ResponseEntity<UsersResponseDTO> create (@Valid @RequestBody UsersRequestDTO dto){
       Users createUser = service.create(UsersMapper.toUser(dto));
       return ResponseEntity.status(HttpStatus.CREATED).body(UsersMapper.toDto(createUser));
    }
}
