package com.eduardo.eventosapi.web.dtos.mapper;

import com.eduardo.eventosapi.entities.Event;
import com.eduardo.eventosapi.web.dtos.EventRequestDTO;
import com.eduardo.eventosapi.web.dtos.EventResponseDTO;
import com.eduardo.eventosapi.web.dtos.UsersResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static Event toEvent(EventRequestDTO dto){
        return new ModelMapper().map(dto,Event.class);
    }

    public static EventResponseDTO toDto(Event event){
        return new ModelMapper().map(event, EventResponseDTO.class);
    }

    public static List<EventResponseDTO> toListDto(List<Event> events){
        return events.stream().map(e -> toDto(e)).collect(Collectors.toList());
    }
}
