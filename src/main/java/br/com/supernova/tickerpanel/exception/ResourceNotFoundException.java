package br.com.supernova.tickerpanel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id){
        super(String.format("The stock with ID - %d was not found in the system!", id));
    }

    public ResourceNotFoundException(String name){
        super(String.format("Stock with name %s was not found in the system!", name));
    }
}
