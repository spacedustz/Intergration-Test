package com.converter.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {

    @Getter
    private HttpStatus status;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}