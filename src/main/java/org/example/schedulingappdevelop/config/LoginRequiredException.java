package org.example.schedulingappdevelop.config;

import org.springframework.http.HttpStatus;

public class LoginRequiredException extends ServerException {
    public LoginRequiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
