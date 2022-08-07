package com.lowe.urlshortener.exception;

import java.util.function.Supplier;

public class UrlNotExistException extends Exception {

    public UrlNotExistException(final String message){
        super(message);
    }
}
