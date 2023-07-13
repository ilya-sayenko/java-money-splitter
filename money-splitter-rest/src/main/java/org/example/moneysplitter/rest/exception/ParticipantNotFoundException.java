package org.example.moneysplitter.rest.exception;

public class ParticipantNotFoundException extends ModelNotFoundException {
    public ParticipantNotFoundException() {
        super("Participant not found");
    }
}
