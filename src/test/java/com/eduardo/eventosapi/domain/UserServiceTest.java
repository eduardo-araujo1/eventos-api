package com.eduardo.eventosapi.domain;

import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.exception.EmailUniqueViolation;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final User USER = new User(1L, "John Doe", "password123", "john@example.com", "123456789");
    public static final User INVALID_USER = new User(2L, "", "", "", "");

    public static final User USER1 = new User(3L, "Alice", "pass123", "alice@example.com", "987654321");
    public static final User USER2 = new User(4L, "Bob", "secret456", "bob@example.com", "555555555");

    public static final List<User> USER_LIST = new ArrayList<User>() {
        {
            add(USER1);
            add(USER2);
        }
    };

    @Mock
    private UsersRepository repository;

    @InjectMocks
    private UsersService userService;


    @Test
    public void createUser_WithValidData_ReturnsUser() {
        when(repository.save(USER)).thenReturn(USER);

        User sut = userService.create(USER);

        assertThat(sut).isEqualTo(USER);
    }

    @Test
    public void createUser_WithInvalidData_ThrowsException() {
        when(repository.save(INVALID_USER)).thenThrow(EmailUniqueViolation.class);

        assertThatThrownBy(() -> userService.create(INVALID_USER)).isInstanceOf(EmailUniqueViolation.class);
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() {
        when(repository.findById(3L)).thenReturn(Optional.of(USER1));

        Optional<User> sut = Optional.ofNullable(userService.findById(3L));

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(USER1);
    }

    @Test
    public void getUser_ByUnexistingId_ThrowsException() {
        when(repository.findById(10L)).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> userService.findById(10L)).isInstanceOf(EntityNotFoundException.class);
    }




}
