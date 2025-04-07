package com.upe.br.auth.handlers;

import com.upe.br.auth.dtos.ErrorResponse;
import com.upe.br.auth.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handlesAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlesException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e));
    }
}
