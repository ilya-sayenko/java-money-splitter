package org.example.moneysplitter.rest.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.moneysplitter.rest.exception.IncorrectDataException;
import org.example.moneysplitter.rest.exception.IncorrectDeleteException;
import org.example.moneysplitter.rest.exception.ModelNotFoundException;
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
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException ex) {
        log.warn(ex.getMessage());
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(notFound)
                .body(ErrorResponse
                        .builder()
                        .status(notFound.value())
                        .errorCode(-1004)
                        .errorMessage(ex.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIncorrectDataException(IncorrectDataException ex) {
        log.warn(ex.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse
                        .builder()
                        .status(badRequest.value())
                        .errorCode(-1005)
                        .errorMessage(ex.getMessage())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIncorrectDeleteException(IncorrectDeleteException ex) {
        log.warn(ex.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(badRequest)
                .body(ErrorResponse
                        .builder()
                        .status(badRequest.value())
                        .errorCode(-1006)
                        .errorMessage(ex.getMessage())
                        .build());
    }
}
