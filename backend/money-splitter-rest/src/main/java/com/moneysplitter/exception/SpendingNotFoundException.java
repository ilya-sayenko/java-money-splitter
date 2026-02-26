package com.moneysplitter.exception;

import java.util.UUID;

public class SpendingNotFoundException extends ModelNotFoundException {

    public SpendingNotFoundException(UUID id) {
        super(String.format("Spending with id=%s not found", id));
    }
}
