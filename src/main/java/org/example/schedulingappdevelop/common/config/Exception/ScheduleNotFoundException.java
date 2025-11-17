package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServerException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
