package com.example.ecomuserservice.exceptions;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException(String wrongCredentials) {
        super(wrongCredentials);
    }
}
