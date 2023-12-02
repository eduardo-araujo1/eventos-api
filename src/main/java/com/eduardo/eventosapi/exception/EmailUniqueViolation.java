package com.eduardo.eventosapi.exception;

public class EmailUniqueViolation extends RuntimeException {
    public EmailUniqueViolation(String message){
        super(message);
    }
}
