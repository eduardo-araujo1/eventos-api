package com.eduardo.eventosapi.web;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.services.UsersService;
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
    public ResponseEntity<Users> create (@RequestBody Users users){
        Users createUsers = service.create(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUsers);
    }
}
