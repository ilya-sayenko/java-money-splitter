package org.example.moneysplitter.party.exception;

public class ParticipantNotFoundException extends ModelNotFoundException {
    public ParticipantNotFoundException() {
        super("Participant not found");
    }
}
