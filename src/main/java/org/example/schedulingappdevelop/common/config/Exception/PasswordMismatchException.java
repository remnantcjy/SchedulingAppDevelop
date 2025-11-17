package org.example.schedulingappdevelop.common.config.Exception;

import org.example.schedulingappdevelop.common.config.ServerException;
import org.springframework.http.HttpStatus;

// 이메일 - 비밀번호 불일치 예외처리
// 반드시 RuntimeException을 상속해야 함
public class PasswordMismatchException extends ServerException {
    public PasswordMismatchException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
