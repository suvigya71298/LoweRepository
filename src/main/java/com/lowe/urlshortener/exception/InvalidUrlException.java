package com.lowe.urlshortener.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class InvalidUrlException extends ResponseStatusException {

    public InvalidUrlException(final HttpStatus status, final String message){

        super(status, message);
        log.error("Status {},message {}", status,message);
    }

}
