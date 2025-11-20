package org.example.schedulingappdevelop.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.dto.*;
import org.example.schedulingappdevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponse> signup(
            @Valid @RequestBody SignupUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {

        // SessionUser = 세션 저장용 DTO
        SessionUser sessionUser = userService.login(request);

        // 로그인 상태를 만드는 핵심 로직
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 유저 조회 - 다건
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    // 유저 조회 - 단건
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOne(
            @PathVariable Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    // 유저 수정
//    @PutMapping("/users/{userId}")
    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> update(
            @Valid @RequestBody UpdateUserRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(sessionUser, request));
    }

    // 유저 삭제 - 회원 탈퇴
    @DeleteMapping("/users")
    public ResponseEntity<Void> delete(
            @Valid @RequestBody DeleteUserRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            HttpSession session
    ) {
        userService.delete(sessionUser, request);

        // 회원 탈퇴 후, 세션 끊어줌
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
