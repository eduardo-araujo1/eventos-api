package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.exception.DataBaseException;
import com.eduardo.eventosapi.exception.EmailUniqueViolation;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;

    @Transactional
    public Users create(Users users) {
        try {
            return repository.save(users);
        } catch (DataIntegrityViolationException ex) {
            throw new EmailUniqueViolation(String.format("Email '%s' já cadastrado", users.getEmail()));
        }

    }

    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id=%s não encontrado", id)));
    }

    @Transactional
    public Users update(Long id, UsersRequestDTO dto) {
        try {
            Users existingUser = findById(id);

            existingUser.setName(dto.getName());
            existingUser.setCpf(dto.getCpf());
            existingUser.setEmail(dto.getEmail());
            existingUser.setPassword(dto.getPassword());

            return repository.save(existingUser);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }
    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return repository.findAll();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new DataBaseException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Falha de integridade referencial");
        }
    }
}
