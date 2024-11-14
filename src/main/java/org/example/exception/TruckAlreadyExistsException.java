package org.example.exception;

public class TruckAlreadyExistsException extends RuntimeException {
    public TruckAlreadyExistsException(String message) {
        super(message);
    }
}
