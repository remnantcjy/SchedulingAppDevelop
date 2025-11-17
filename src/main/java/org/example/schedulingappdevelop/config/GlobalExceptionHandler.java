package org.example.schedulingappdevelop.config;

import org.example.schedulingappdevelop.config.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 커스텀 에러를 핸들링
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 에러 핸들링
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())     // 커스텀 에러마다 동적인 에러 코드 설정 가능 !
                .body(response);
    }

}
