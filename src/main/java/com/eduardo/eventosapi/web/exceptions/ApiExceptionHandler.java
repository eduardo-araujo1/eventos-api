package com.eduardo.eventosapi.web.exceptions;

import com.eduardo.eventosapi.exception.EmailUniqueViolation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EmailUniqueViolation.class)
    public ResponseEntity<ErrorMessage> emailUniqueViolation(EmailUniqueViolation e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorMessage err = new ErrorMessage(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
