package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.exception.EmailUniqueViolation;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.web.dtos.request.UsersRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;

    @Transactional
    public User create(User user) {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new EmailUniqueViolation(String.format("Email '%s' já cadastrado", user.getEmail()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario id=%s não encontrado", id)));
    }

    @Transactional
    public User update(Long id, UsersRequestDTO dto) {
        try {
            User existingUser = findById(id);

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
    public Page<User> findAll(int page, int itens) {
        return repository.findAll(PageRequest.of(page, itens));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}
