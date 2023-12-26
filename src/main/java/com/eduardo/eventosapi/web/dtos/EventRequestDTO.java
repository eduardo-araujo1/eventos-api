package com.eduardo.eventosapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EventRequestDTO {

    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String location;
    private LocalDate date;

}
