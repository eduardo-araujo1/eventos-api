package com.eduardo.eventosapi.service;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.exception.DataBaseException;
import com.eduardo.eventosapi.exception.EmailUniqueViolation;
import com.eduardo.eventosapi.exception.EntityNotFoundException;
import com.eduardo.eventosapi.exception.ResourceNotFoundException;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.services.UsersService;
import com.eduardo.eventosapi.web.dtos.request.UsersRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final User USER = new User(1L, "John Doe", "password123", "john@example.com", "123456789");
    public static final User INVALID_USER = new User(2L, "", "", "", "");

    public static final User USER1 = new User(3L, "Alice", "pass123", "alice@example.com", "987654321");
    public static final User USER2 = new User(4L, "Bob", "secret456", "bob@example.com", "555555555");

    public static final List<User> USER_LIST = Arrays.asList(USER1, USER2);

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

    @Test
    public void updateUser_WithValidData_ReturnsUpdatedUser() {
        when(repository.findById(1L)).thenReturn(Optional.of(USER));

        UsersRequestDTO updatedUserData = new UsersRequestDTO("Updated Name",
                "updatedPassword", "updated@example.com", "987654321");

        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updatedUser = userService.update(1L, updatedUserData);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getPassword()).isEqualTo("updatedPassword");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedUser.getCpf()).isEqualTo("987654321");

        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    public void updateUser_WithInvalidData_ReturnsUpdatedUser() {

        when(repository.findById(2L)).thenReturn(Optional.of(USER));

        when(repository.findById(2L)).thenThrow(EntityNotFoundException.class);


        UsersRequestDTO updatedUserData = new UsersRequestDTO("", "", "", "");


        assertThatThrownBy(() -> userService.update(2L, updatedUserData))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any(User.class));
    }


    @Test
    public void getAllUsers_ReturnsListOfUsers() {
        Page<User> userPage = new PageImpl<>(USER_LIST);
        when(repository.findAll(PageRequest.of(0, 10))).thenReturn(userPage);

        Page<User> users = userService.findAll(0, 10);

        assertThat(users.getContent()).isNotNull().hasSize(2);
    }

    @Test
    public void getAllUsers_ReturnsEmptyList() {
        Page<User> emptyUserPage = new PageImpl<>(Collections.emptyList());

        when(repository.findAll((Pageable) any())).thenReturn(emptyUserPage);

        Page<User> users = userService.findAll(0,10);

        assertThat(users.getContent()).isNotNull().isEmpty();
    }


    @Test
    public void deleteUser_ByExistingId_DeletesUser() {
        when(repository.existsById(1L)).thenReturn(true);

        userService.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUser_ByNonExistingId_ThrowsException() {
        when(repository.existsById(10L)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteById(10L)).isInstanceOf(ResourceNotFoundException.class);
    }


}
