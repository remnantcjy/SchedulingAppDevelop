package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

public class LoginRequiredException extends ServerException {
    public LoginRequiredException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
