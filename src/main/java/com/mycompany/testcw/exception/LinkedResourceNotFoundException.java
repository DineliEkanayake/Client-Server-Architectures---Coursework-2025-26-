package com.mycompany.testcw.exception;

public class LinkedResourceNotFoundException extends RuntimeException {
    public LinkedResourceNotFoundException(String message){
        super(message);
    }
}
