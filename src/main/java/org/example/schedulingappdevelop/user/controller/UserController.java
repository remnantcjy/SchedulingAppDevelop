package org.example.schedulingappdevelop.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.user.dto.CreateUserRequest;
import org.example.schedulingappdevelop.user.dto.CreateUserResponse;
import org.example.schedulingappdevelop.user.dto.GetUserResponse;
import org.example.schedulingappdevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
