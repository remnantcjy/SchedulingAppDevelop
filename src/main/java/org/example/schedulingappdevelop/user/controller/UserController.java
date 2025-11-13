package org.example.schedulingappdevelop.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.dto.CreateUserRequest;
import org.example.schedulingappdevelop.user.dto.CreateUserResponse;
import org.example.schedulingappdevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }
}
