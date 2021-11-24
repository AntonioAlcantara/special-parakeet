package com.parakeet.lol.exception;

public class StudentExistsException extends Exception {

    public StudentExistsException(String passportNumber) {
        super(String.format("The student with the passport number %s already exists in our database", passportNumber));
    }
}
