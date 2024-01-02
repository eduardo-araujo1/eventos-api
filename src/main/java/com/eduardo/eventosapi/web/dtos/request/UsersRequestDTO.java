package com.eduardo.eventosapi.web.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequestDTO {

    @Size(min = 3, message = "Campo deve ter no mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "Campo requerido")
    private String password;

    @NotBlank
    @Email(message = "Formato do email está inválido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;
}
