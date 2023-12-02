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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
