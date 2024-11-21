package com.trree.rattattouille.exception;

import lombok.Getter;

@Getter
public class WebClientResponseException extends RuntimeException{

    public WebClientResponseException(String message) {
        super(message);
    }
}
