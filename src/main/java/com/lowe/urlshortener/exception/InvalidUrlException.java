package com.lowe.urlshortener.exception;

public class InvalidUrlException extends Exception{

    public InvalidUrlException(final String message){
        super(message);
    }

}
