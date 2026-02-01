package com.simon.exception;

public class RemoveMemberWithActiveRentalsException extends RuntimeException {
    public RemoveMemberWithActiveRentalsException(String message) {
        super(message);
    }
}
