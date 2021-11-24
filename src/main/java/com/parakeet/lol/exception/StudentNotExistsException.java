package com.parakeet.lol.exception;

public class StudentNotExistsException extends Exception {

    public StudentNotExistsException(long studentId) {
        super(String.format("The student with Id %s not exists in our database", studentId));
    }
}
