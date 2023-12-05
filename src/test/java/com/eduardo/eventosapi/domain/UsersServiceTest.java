package com.eduardo.eventosapi.domain;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    public static final Users USER = new Users(1L, "John Doe", "password123", "john@example.com", "123456789");
    public static final Users INVALID_USER = new Users(2L, "", "", "", "");

    public static final Users USER1 = new Users(1L, "Alice", "pass123", "alice@example.com", "987654321");
    public static final Users USER2 = new Users(2L, "Bob", "secret456", "bob@example.com", "555555555");

    public static final List<Users> USERS_LIST = new ArrayList<Users>() {
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
    public void createUser_WithValidData_ReturnsUser(){
        when(repository.save(USER)).thenReturn(USER);

        Users sut = userService.create(USER);

        assertThat(sut).isEqualTo(USER);
    }
}
