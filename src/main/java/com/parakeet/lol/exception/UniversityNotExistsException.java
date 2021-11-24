package com.parakeet.lol.exception;

public class UniversityNotExistsException extends Exception {

    public UniversityNotExistsException() {
        super("University not found");
    }
}
