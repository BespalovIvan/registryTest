package com.respak.registryTest.exception;

public class FarmerNotFoundException extends RuntimeException{
    public FarmerNotFoundException(String message) {
        super(message);
    }
}
