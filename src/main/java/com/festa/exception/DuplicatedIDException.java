package com.festa.exception;

public class DuplicatedIDException extends RuntimeException {
    public DuplicatedIDException(String message) {
        super(message);
    }
}
