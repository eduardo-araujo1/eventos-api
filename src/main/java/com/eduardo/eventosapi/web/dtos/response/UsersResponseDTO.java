package com.eduardo.eventosapi.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
}
