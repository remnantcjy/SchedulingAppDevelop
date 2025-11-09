package org.example.schedulingappdevelop.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private String author;
    private String title;
    private String contents;
}
