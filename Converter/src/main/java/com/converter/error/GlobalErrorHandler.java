package com.converter.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
    private static final Logger log = LogManager.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonExceptionResponse> commonExceptionHandler(CommonException e) {
        CommonExceptionResponse error = new CommonExceptionResponse(e.getStatus().value(), e.getMessage() != null ? e.getMessage() : "나도 몰라요");
        return ResponseEntity.status(e.getStatus()).body(error);
    }
}