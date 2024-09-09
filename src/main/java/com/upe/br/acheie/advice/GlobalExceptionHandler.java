package com.upe.br.acheie.advice;

import com.upe.br.acheie.domain.dto.ErroDto;
import com.upe.br.acheie.domain.exceptions.AcheieException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(AcheieException.class)
  public ResponseEntity<ErroDto> manipulaAcheiException(AcheieException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDto(e));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErroDto> manipulaException(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDto(e));
  }

}
