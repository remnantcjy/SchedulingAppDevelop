package org.example.schedulingappdevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long userId;
    private final String scheduleUsername;
    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final Long commentId;
    private final String commentUserName;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Long userId, String scheduleUsername, Long scheduleId, String title, String contents, Long commentId, String commentUserName, String comment, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.scheduleUsername = scheduleUsername;
        this.scheduleId = scheduleId;
        this.title = title;
        this.contents = contents;
        this.commentId = commentId;
        this.commentUserName = commentUserName;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
