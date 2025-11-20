package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ServerException {
    public CommentNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
