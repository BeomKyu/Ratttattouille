package com.trree.rattattouille.global.exception;

import lombok.Getter;

@Getter
public class WebClientResponseException extends RuntimeException{

    public WebClientResponseException(String message) {
        super(message);
    }
}
