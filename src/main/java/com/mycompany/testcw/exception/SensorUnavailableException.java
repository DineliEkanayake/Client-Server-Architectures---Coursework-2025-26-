package com.mycompany.testcw.exception;

public class SensorUnavailableException extends RuntimeException{
    public SensorUnavailableException(String message){
        super(message);
    }
}
