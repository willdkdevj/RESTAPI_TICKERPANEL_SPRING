package br.com.supernova.tickerpanel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ResourceAlreadyRegisteredException extends Exception {

    public ResourceAlreadyRegisteredException(String message) {
        super(String.format("Stock %s was already registered!", message));
    }
}
