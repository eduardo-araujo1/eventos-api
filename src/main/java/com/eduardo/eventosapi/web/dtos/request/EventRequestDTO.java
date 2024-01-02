package com.eduardo.eventosapi.web.dtos.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    @NotBlank(message = "O título do evento é obrigatório")
    @Size(min = 3, max = 255, message = "O título do evento deve ter entre 3 e 255 caracteres")
    private String title;

    @Size(min = 3, message = "A descrição do evento deve ter pelo menos 3 caracteres")
    private String description;

    @NotBlank(message = "O local do evento é obrigatório")
    @Size(min = 5, message = "O local do evento deve ter pelo menos 5 caracteres")
    private String location;

    @NotBlank
    @Future(message = "A data do evento deve ser no futuro")
    private LocalDate date;
}
