package com.eduardo.eventosapi.controller;

import com.eduardo.eventosapi.web.dtos.request.EventRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.EventResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/event/events-insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/event/events-delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EventControllerTestIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void createEvent_thenReturnCreated() {
        ResponseEntity<EventResponseDTO> responseEntity = testRestTemplate
                .postForEntity("/events",
                        new EventRequestDTO("Corrida", "Corrida da amizade", "São Paulo", LocalDate.of(2024, 3, 3)), EventResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        EventResponseDTO responseDTO = responseEntity.getBody();
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isNotNull(); // O ID deve ser gerado automaticamente
        assertThat(responseDTO.getTitle()).isEqualTo("Corrida");
        assertThat(responseDTO.getDescription()).isEqualTo("Corrida da amizade");
        assertThat(responseDTO.getLocation()).isEqualTo("São Paulo");
    }
    //tem que implementar a excecão no service
    @Test
    public void createEvent_thenReturnConflictStatus() {
        ResponseEntity<EventResponseDTO> responseEntity = testRestTemplate
                .postForEntity("/events",
                        new EventRequestDTO("Futebol", "Futebol dos amigos", "São paulo", LocalDate.of(2024, 5, 2)), EventResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void findEventById_thenReturnUser() {
        ResponseEntity<EventResponseDTO> responseEntity = testRestTemplate
                .getForEntity("/events/200", EventResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        EventResponseDTO responseDTO = responseEntity.getBody();
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(200);
        assertThat(responseDTO.getTitle()).isEqualTo("Futebol");
        assertThat(responseDTO.getDescription()).isEqualTo("Futebol dos amigos");
    }

    @Test
    public void findEventById_thenReturnNotFound() {
        ResponseEntity<EventResponseDTO> responseEntity = testRestTemplate
                .getForEntity("/events/3", EventResponseDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllEvents_thenReturnStatusOK(){
        ResponseEntity<List> responseEntity = testRestTemplate
                .getForEntity("/events", List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<EventResponseDTO> responseDtos = (List<EventResponseDTO>) responseEntity.getBody();
        assertThat(responseDtos).isNotNull();
        assertThat(responseDtos.size()).isEqualTo(1);
    }


}
