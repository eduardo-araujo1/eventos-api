package com.eduardo.eventosapi.web.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long eventId;
}
