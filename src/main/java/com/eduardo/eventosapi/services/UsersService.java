package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Users update(Long id, UsersRequestDTO dto) {
        Users existingUser = findById(id);

        existingUser.setName(dto.getName());
        existingUser.setCpf(dto.getCpf());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPassword(dto.getPassword());

        return repository.save(existingUser);
    }


    public List<Users> findAll() {
        return repository.findAll();
    }
}
