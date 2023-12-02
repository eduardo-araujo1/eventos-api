package com.eduardo.eventosapi.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequestDTO {

    @Size(min = 3, message = "Campo deve ter no minimo 3 caracteres")
    private String name;
    @NotBlank(message = "Campo requerido")
    private String password;
    @NotBlank(message = "Campo requerido")
    private String email;
    @NotBlank(message = "Campo requerido")
    private String cpf;
}
