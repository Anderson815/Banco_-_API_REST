package com.anderson.banco.exceptions;

public class RequestConstraintException extends RuntimeException{
    public RequestConstraintException(String msg){
        super(msg);
    }
}
