package org.example.moneysplitter.rest.exception;

public class PartyNotFoundException extends ModelNotFoundException {
    public PartyNotFoundException() {
        super("Party not found");
    }

    public PartyNotFoundException(String message) {
        super(message);
    }
}
