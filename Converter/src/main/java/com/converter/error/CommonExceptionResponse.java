package com.converter.error;

public class CommonExceptionResponse {
    private int code;
    private String message;

    public CommonExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}