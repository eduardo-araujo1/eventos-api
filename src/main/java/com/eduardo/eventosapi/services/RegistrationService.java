package com.eduardo.eventosapi.services;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import com.eduardo.eventosapi.repositories.RegistrationRepository;
import com.eduardo.eventosapi.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UsersRepository usersRepository;
    private final EventRepostirory eventRepostirory;

    @Transactional
    public void registerUserForEvent(Long userId, Long eventId){
        User user = usersRepository.findById(userId).orElseThrow(RuntimeException::new);
        Event event = eventRepostirory.findById(eventId).orElseThrow(RuntimeException::new);

        if (isUserAlreadyRegistered(user,event)){
            throw new RuntimeException("Usuario já está registrado para o evento");
        }

        Registration registration = new Registration(user,event, LocalDate.now());
        registrationRepository.save(registration);

    }

    private boolean isUserAlreadyRegistered(User user, Event event){
        return user.getRegistrations().stream().anyMatch(
                registration -> registration.getEvent().equals(event)
        );
    }
}
