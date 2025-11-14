package org.example.schedulingappdevelop.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private Long userId;    // User 엔티티의 id - 이름 무조건 스네이크 케이스 안 됨, 카멜케이스여야 됨. 문제생길 가능성 높음
    private String title;
    private String contents;
}
