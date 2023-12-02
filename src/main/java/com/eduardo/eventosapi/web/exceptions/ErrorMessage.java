package com.eduardo.eventosapi.web.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private Instant timeStamp;
    private Integer status;
    private String error;
    private String path;
}
