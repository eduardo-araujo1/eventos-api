package com.eduardo.eventosapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class EventResponseDTO {

    private Long id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String location;
    private LocalDate date;
}
