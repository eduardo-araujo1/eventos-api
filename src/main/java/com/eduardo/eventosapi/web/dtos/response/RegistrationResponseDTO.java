package com.eduardo.eventosapi.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

    private Long id;
    private Long userId;
    private Long eventId;
    private LocalDate registrationDate;

    private String email;
    private String title;
}
