package com.moneysplitter.exception;

import java.util.UUID;

public class PartyNotFoundException extends ModelNotFoundException {

    public PartyNotFoundException(UUID id) {
        super(String.format("Party with id=%s not found", id));
    }
}
