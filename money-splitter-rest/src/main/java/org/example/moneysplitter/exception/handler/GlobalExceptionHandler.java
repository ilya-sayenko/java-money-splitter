package org.example.moneysplitter.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
        return handleException(new GlobalAppException("Validation error", HttpStatus.BAD_REQUEST, -1003));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(GlobalAppException ex) {
        log.warn(ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse
                        .builder()
                        .status(ex.getHttpStatus().value())
                        .errorCode(ex.getErrorCode())
                        .errorMessage(ex.getMessage())
                        .build());
    }
}
