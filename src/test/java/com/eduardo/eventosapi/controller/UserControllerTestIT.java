package com.eduardo.eventosapi.controller;

import com.eduardo.eventosapi.web.dtos.request.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.UsersResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/users/users-insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/users/users-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTestIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createUser_thenReturnCreated() {
        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .postForEntity("/users",
                        new UsersRequestDTO("joao", "123456", "joao@email.com", "1112223336"), UsersResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        UsersResponseDTO responseDTO = responseEntity.getBody();
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isNotNull();
        assertThat(responseDTO.getName()).isEqualTo("joao");
        assertThat(responseDTO.getEmail()).isEqualTo("joao@email.com");
        assertThat(responseDTO.getCpf()).isEqualTo("1112223336");
    }

    @Test
    public void createUser_thenReturnedConflictStatus() {
        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .postForEntity("/users",
                        new UsersRequestDTO("ana", "123456789", "ana@email.com", "123456789"), UsersResponseDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }


}