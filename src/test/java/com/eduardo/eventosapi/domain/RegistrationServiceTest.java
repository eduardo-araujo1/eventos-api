package com.eduardo.eventosapi.domain;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.repositories.EventRepostirory;
import com.eduardo.eventosapi.repositories.RegistrationRepository;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.services.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private EventRepostirory eventRepostirory;

    @InjectMocks
    private RegistrationService registrationService;


    @Test
    public void registerUserForEvent_WithValidData_RegistersUser() {
        User user = new User(1L, "John Doe", "password123", "john@example.com", "123456789");
        Event event = new Event(1L, "Event Name", "Description", "Location", LocalDate.now().plusDays(7));

        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepostirory.findById(1L)).thenReturn(Optional.of(event));
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThatCode(() -> registrationService.registerUserForEvent(1L, 1L)).doesNotThrowAnyException();

        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    public void registerUserForEvent_UserNotFound_ThrowsEntityNotFoundException() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.registerUserForEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    public void registerUserForEvent_EventNotFound_ThrowsEntityNotFoundException() {
        User user = new User(1L, "John Doe", "password123", "john@example.com", "123456789");
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepostirory.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> registrationService.registerUserForEvent(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(registrationRepository, never()).save(any(Registration.class));
    }


    @Test
    public void getRegistrationsByUserId_ReturnsListOfRegistrations() {
        List<Registration> registrations = Arrays.asList(
                new Registration(new User(1L), new Event(1L), LocalDate.now()),
                new Registration(new User(1L), new Event(2L), LocalDate.now())
        );

        when(registrationRepository.findByUserId(1L)).thenReturn(registrations);

        List<Registration> result = registrationService.getRegistrationsByUserId(1L);

        assertThat(result).isNotNull().hasSize(2);
    }

    @Test
    public void getRegistrationsByEventId_ReturnsListOfRegistrations() {
        List<Registration> registrations = Arrays.asList(
                new Registration(new User(1L), new Event(1L), LocalDate.now()),
                new Registration(new User(2L), new Event(1L), LocalDate.now())
        );

        when(registrationRepository.findByEventId(1L)).thenReturn(registrations);

        List<Registration> result = registrationService.getRegistrationsByEventId(1L);

        assertThat(result).isNotNull().hasSize(2);
    }
}
