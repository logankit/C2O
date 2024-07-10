package com.equifax.c2o.api.exception;

public class ApiException extends RuntimeException {
    private String code;
    private String message;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
