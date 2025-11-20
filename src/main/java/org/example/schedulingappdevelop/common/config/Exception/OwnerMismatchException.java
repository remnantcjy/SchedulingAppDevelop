package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

public class OwnerMismatchException extends ServerException {
    public OwnerMismatchException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
