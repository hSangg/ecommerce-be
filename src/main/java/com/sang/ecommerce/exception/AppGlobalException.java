package com.sang.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppGlobalException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public AppGlobalException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

}
