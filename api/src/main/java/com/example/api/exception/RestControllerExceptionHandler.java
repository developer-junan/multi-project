package com.example.api.exception;

import com.example.core.exception.ApiException;
import com.example.core.exception.ErrorResponseVO;
import com.example.core.exception.ErrorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO unknownException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_9999, ex.getMessage());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseVO missingServletRequestParameterException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ErrorTypeEnum.ERROR_0001, ex.getMessage());
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<?> apiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getErrorType(), ex.getAdditionalMessage()));
    }

    private ErrorResponseVO getErrorResponse(ErrorTypeEnum errorTypeEnum, String additionalMessage) {
        return ErrorResponseVO.builder()
                .errorCode(errorTypeEnum.getErrorCode())
                .errorMessage(errorTypeEnum.getErrorMessage())
                .additionalMessage(additionalMessage)
                .build();
    }
}