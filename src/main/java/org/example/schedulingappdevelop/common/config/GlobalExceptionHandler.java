package org.example.schedulingappdevelop.common.config;

import org.example.schedulingappdevelop.common.config.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {

        // 에러 메시지 모아서 하나로 합치기
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("입력 값이 올바르지 않습니다.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

}
