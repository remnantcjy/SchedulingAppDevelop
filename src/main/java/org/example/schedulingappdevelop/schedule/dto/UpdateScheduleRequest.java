package org.example.schedulingappdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    // 일정 수정 - 제목, 내용만 수정 가능

    @NotBlank @Size(max = 10, message = "제목은 {max}자 이내로 작성해주세요.")
    private String title;
    
    @NotBlank
    private String contents;
}
