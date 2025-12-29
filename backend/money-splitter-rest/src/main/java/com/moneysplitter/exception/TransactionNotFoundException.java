package com.moneysplitter.exception;

import java.util.UUID;

public class TransactionNotFoundException extends ModelNotFoundException {

    public TransactionNotFoundException(UUID id) {
        super(String.format("Transaction with id=%s not found", id));
    }
}
