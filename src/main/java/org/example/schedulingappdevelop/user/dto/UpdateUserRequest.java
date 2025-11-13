package org.example.schedulingappdevelop.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    // id를 PathVariable 대신 요청 body로
    private Long id;

    // 이름만 변경
    private String name;
}
