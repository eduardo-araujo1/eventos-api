package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;


    public Users create(Users users) {
        return repository.save(users);
    }
}
