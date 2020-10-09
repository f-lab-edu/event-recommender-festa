package com.festa.exception;

public class DuplicatedIDException extends IllegalArgumentException {
    public DuplicatedIDException(String message) {
        super(message);
    }
}
