package com.eduardo.eventosapi.repository;

import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindAll(){
        User user1 = new User(1L,"User1","1233456","user@test.com","11122233345");
        User user2 = new User(2L,"User2","1111111","user2@test.com","22222233345");
        usersRepository.saveAll(Arrays.asList(user1,user2));

        Pageable pageable = PageRequest.of(0,10);
        Page<User> resultPage = usersRepository.findAll(pageable);

        assertThat(resultPage.getTotalElements()).isEqualTo(2);
        assertThat(resultPage.getContent()).containsExactly(user1,user2);

    }
}
