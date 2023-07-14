package org.example.moneysplitter.rest.exception;

public class IncorrectDeleteException extends RuntimeException {
    public IncorrectDeleteException(String message) {
        super(message);
    }
}
