package org.example.moneysplitter.cache.exception;

import org.example.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

public class IncorrectCacheException extends GlobalAppException {
    public IncorrectCacheException(String message) {
        super(message, HttpStatus.BAD_REQUEST, -1007);
    }
}
