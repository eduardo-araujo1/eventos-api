package com.eduardo.eventosapi.web.dtos.mapper;

import com.eduardo.eventosapi.entities.Users;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.UsersResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UsersMapper {

    public static Users toUser(UsersRequestDTO dto){
        return new ModelMapper().map(dto, Users.class);
    }

    public static UsersResponseDTO toDto(Users users){
        return new ModelMapper().map(users, UsersResponseDTO.class);
    }

    public static List<UsersResponseDTO> toListDto(List<Users> users){
        return users.stream().map(u -> toDto(u)).collect(Collectors.toList());
    }
}
