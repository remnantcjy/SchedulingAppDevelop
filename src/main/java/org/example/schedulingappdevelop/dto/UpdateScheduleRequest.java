package org.example.schedulingappdevelop.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    // 일정 수정 - 해당 id의 일정: 제목, 내용만 수정 가능
    private Long id;
    private String title;
    private String contents;
}
