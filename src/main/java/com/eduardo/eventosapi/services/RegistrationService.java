package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.exception.RegistrationException;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import com.eduardo.eventosapi.repositories.RegistrationRepository;
import com.eduardo.eventosapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UsersRepository usersRepository;
    private final EventRepostirory eventRepostirory;

    @Transactional
    public void registerUserForEvent(Long userId, Long eventId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        Event event = eventRepostirory.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com o ID:" + eventId));

        if (isUserAlreadyRegistered(user, event)) {
            throw new RegistrationException("Usuario já está registrado para o evento.");
        }

        Registration registration = new Registration(user, event, LocalDate.now());
        registrationRepository.save(registration);

    }

    public boolean isUserAlreadyRegistered(User user, Event event) {
        return Optional.ofNullable(user.getRegistrations())
                .map(registrations -> registrations
                        .stream().anyMatch(registration -> event.equals(registration.getEvent())))
                .orElse(false);
    }


    @Transactional(readOnly = true)
    public List<Registration> getRegistrationsByUserId(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Registration> getRegistrationsByEventId(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }
}
