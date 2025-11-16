package org.example.schedulingappdevelop.config;

import org.springframework.http.HttpStatus;

public class SessionExpiredException extends ServerException {
    public SessionExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
