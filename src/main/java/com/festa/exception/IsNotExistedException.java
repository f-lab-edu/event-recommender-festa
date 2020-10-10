package com.festa.exception;

/**
 * 찾는 것이 존재하지 않을 경우 발생하는 예외
 */
public class IsNotExistedException extends IllegalArgumentException {
    public IsNotExistedException(String message) {
        super(message);
    }
}
