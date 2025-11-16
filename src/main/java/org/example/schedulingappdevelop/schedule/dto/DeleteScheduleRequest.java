package org.example.schedulingappdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    @NotBlank
    private String password;
}
