package com.project.orderaccess.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);

        var errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorStatus)
                .body(new ExceptionResponse(errorStatus.value(), e.getMessage()));
    }

}
