package com.simsekali.awssqsdemo.exception;

public class UserAlreadyRegistered extends BaseException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
