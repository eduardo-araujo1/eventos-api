package com.eduardo.eventosapi.web.dtos.mapper;

import com.eduardo.eventosapi.entities.Registration;
import com.eduardo.eventosapi.web.dtos.request.RegistrationRequestDTO;
import com.eduardo.eventosapi.web.dtos.response.RegistrationResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class RegistrationMapper {

    public static Registration toRegistration(RegistrationRequestDTO dto){
        return new ModelMapper().map(dto, Registration.class);
    }

    public static RegistrationResponseDTO toDto(Registration registration) {
        RegistrationResponseDTO dto = new RegistrationResponseDTO();
        dto.setId(registration.getId());
        dto.setUserId(registration.getUser().getId());
        dto.setEventId(registration.getEvent().getId());
        dto.setRegistrationDate(registration.getRegistrationDate());


        dto.setEmail(registration.getUser().getEmail());
        dto.setTitle(registration.getEvent().getTitle());

        return dto;
    }

    public static List<RegistrationResponseDTO> toListDto(List<Registration> registrations) {
        return registrations.stream().map(RegistrationMapper::toDto).collect(Collectors.toList());
    }

}

