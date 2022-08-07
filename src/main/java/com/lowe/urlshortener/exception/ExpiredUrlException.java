package com.lowe.urlshortener.exception;

public class ExpiredUrlException extends Exception{

    public ExpiredUrlException(final String message){
        super(message);
    }
}
