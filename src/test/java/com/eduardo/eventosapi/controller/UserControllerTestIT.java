package com.eduardo.eventosapi.controller;

import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.repositories.UsersRepository;
import com.eduardo.eventosapi.web.dtos.request.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.UsersResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

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
    public void createUser_thenReturnConflictStatus() {
        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .postForEntity("/users",
                        new UsersRequestDTO("ana", "123456789", "ana@email.com", "123456789"), UsersResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void findUserById_thenReturnUser() {
        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .getForEntity("/users/100", UsersResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsersResponseDTO responseDTO = responseEntity.getBody();
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(100);
        assertThat(responseDTO.getName()).isEqualTo("ana");
        assertThat(responseDTO.getEmail()).isEqualTo("ana@email.com");
    }

    @Test
    public void findUserById_thenReturnNotFound() {
        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .getForEntity("/users/3", UsersResponseDTO.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    public void updateUser_thenReturnUpdatedUser() {
        UsersRequestDTO updateRequest = new UsersRequestDTO("updatedName", "newPassword", "new@email.com", "987654321");

        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .exchange("/users/100", HttpMethod.PUT, new HttpEntity<>(updateRequest), UsersResponseDTO.class, updateRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        UsersResponseDTO responseDTO = responseEntity.getBody();
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(100);
        assertThat(responseDTO.getName()).isEqualTo("updatedName");
        assertThat(responseDTO.getEmail()).isEqualTo("new@email.com");
        assertThat(responseDTO.getCpf()).isEqualTo("987654321");
    }

    @Test
    public void updateUser_thenReturnNotFoundStatus(){
        UsersRequestDTO requestDTO = new UsersRequestDTO("updatedName", "newPassword", "new@email.com", "987654321");

        ResponseEntity<UsersResponseDTO> responseEntity = testRestTemplate
                .exchange("/users/3", HttpMethod.PUT, new HttpEntity<>(requestDTO),UsersResponseDTO.class, requestDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllUsers_thenReturnStatusOK(){
        ResponseEntity<List> responseEntity = testRestTemplate
                .getForEntity("/users", List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<UsersResponseDTO> responseDtos = (List<UsersResponseDTO>) responseEntity.getBody();
        assertThat(responseDtos).isNotNull();
        assertThat(responseDtos.size()).isEqualTo(3);
    }

    @Test
    public void deleteUserById_thenReturnNoContentStatus(){
        ResponseEntity<Void> responseEntity = testRestTemplate
                .exchange("/users/100", HttpMethod.DELETE,null,Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    
}

