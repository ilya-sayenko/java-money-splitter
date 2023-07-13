package org.example.moneysplitter.rest.exception;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException() {
        super("Incorrect data");
    }
}
