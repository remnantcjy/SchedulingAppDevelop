package org.example.schedulingappdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long userId;
    private final String username;
    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long userId, String username, Long scheduleId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
