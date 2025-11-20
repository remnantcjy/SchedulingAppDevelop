package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

public class CommentsExistException extends ServerException {
    public CommentsExistException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
