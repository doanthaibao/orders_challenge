package com.interview.codepole.exception;

import com.interview.codepole.api.ErrorResponseV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseV1 handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Illegal argument exception", e);
        return ErrorResponseV1.from(e.getMessage(), ApplicationErrors.ILLEGAL_ARGUMENT.getValue());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseV1 handleInternalError(Exception e) {
        log.error("Exception exception", e);
        return ErrorResponseV1.from(e.getMessage(), ApplicationErrors.INTERNAL_ERROR.getValue());
    }

}
