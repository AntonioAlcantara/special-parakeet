package com.parakeet.lol.exception;

public class StudentIsAlreadySubscribedException extends Exception {

    public StudentIsAlreadySubscribedException() {
        super("The student is already subscribed to this university");
    }
}
