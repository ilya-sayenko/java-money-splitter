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
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Validation error");
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse.builder()
                        .status(badRequest.value())
                        .errorCode(-1003)
                        .errorMessage("Validation error")
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGlobalAppException(GlobalAppException ex) {
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
