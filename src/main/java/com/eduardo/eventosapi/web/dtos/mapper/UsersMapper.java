package com.eduardo.eventosapi.web.dtos.mapper;

import com.eduardo.eventosapi.entities.User;
import com.eduardo.eventosapi.web.dtos.UsersRequestDTO;
import com.eduardo.eventosapi.web.dtos.UsersResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UsersMapper {

    public static User toUser(UsersRequestDTO dto){
        return new ModelMapper().map(dto, User.class);
    }

    public static UsersResponseDTO toDto(User user){
        return new ModelMapper().map(user, UsersResponseDTO.class);
    }

    public static List<UsersResponseDTO> toListDto(List<User> users){
        return users.stream().map(u -> toDto(u)).collect(Collectors.toList());
    }
}
