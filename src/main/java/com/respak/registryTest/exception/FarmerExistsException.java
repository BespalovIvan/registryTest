package com.respak.registryTest.exception;

public class FarmerExistsException extends RuntimeException{
    public FarmerExistsException(String message) {
        super(message);
    }
}
