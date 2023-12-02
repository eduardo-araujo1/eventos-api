package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;

    @Transactional
    public Users create(Users users) {
        return repository.save(users);
    }
    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario id não encontrado"));
    }
}
