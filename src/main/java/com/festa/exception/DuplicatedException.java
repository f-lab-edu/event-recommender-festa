package com.festa.exception;

public class DuplicatedException extends IllegalArgumentException {
    public DuplicatedException(String message) {
        super(message);
    }
}
