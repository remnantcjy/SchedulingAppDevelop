package org.example.schedulingappdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {

    private final Long userId;
    private final String username;
    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetScheduleResponse(Long userId, String username, Long scheduleId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
