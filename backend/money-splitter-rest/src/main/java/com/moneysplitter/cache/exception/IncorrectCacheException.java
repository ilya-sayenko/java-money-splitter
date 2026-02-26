package com.moneysplitter.cache.exception;

import com.moneysplitter.exception.GlobalAppException;
import org.springframework.http.HttpStatus;

public class IncorrectCacheException extends GlobalAppException {

    public IncorrectCacheException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
