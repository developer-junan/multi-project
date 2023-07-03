package com.example.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private int status;
    private ErrorTypeEnum errorType;
    private String additionalMessage;
    public ApiException(int status, ErrorTypeEnum errorType, String additionalMessage) {
        super(errorType.getErrorMessage());
        this.status = status;
        this.errorType = errorType;
        this.additionalMessage = additionalMessage;
    }
}
