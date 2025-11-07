package com.moneysplitter.exception;

import java.util.UUID;

public class ParticipantNotFoundException extends ModelNotFoundException {

    public ParticipantNotFoundException(UUID id) {
        super(String.format("Participant with id=%s not found", id));
    }
}
