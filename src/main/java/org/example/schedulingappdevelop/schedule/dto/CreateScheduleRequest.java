package org.example.schedulingappdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    // userId 이름 무조건 스네이크 케이스 안 됨, 카멜케이스여야 됨. 문제생길 가능성 높음

    @NotBlank @Size(max = 10, message = "제목은 {max}자 이내로 작성해주세요.")
    private String title;

    @NotBlank
    private String contents;
}
