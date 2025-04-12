package com.upe.br.acheie;

import com.upe.br.acheie.dtos.ErrorDTO;
import com.upe.br.acheie.utils.AcheieException;
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
  public ResponseEntity<ErrorDTO> handlesAcheiException(AcheieException e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDTO> handlesException(Exception e) {
    log.error(e.getMessage(), e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e));
  }

}
