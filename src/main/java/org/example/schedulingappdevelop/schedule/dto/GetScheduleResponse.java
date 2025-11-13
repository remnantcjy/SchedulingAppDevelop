package org.example.schedulingappdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GetScheduleResponse(Long id, String author, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
