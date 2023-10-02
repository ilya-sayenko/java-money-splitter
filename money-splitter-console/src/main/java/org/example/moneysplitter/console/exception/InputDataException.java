package org.example.moneysplitter.console.exception;

import org.example.moneysplitter.console.exception.global.GlobalAppException;

public class InputDataException extends GlobalAppException {
    public InputDataException(String message) {
        super(message);
    }
}
