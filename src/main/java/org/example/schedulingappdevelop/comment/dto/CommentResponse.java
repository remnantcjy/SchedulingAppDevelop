package org.example.schedulingappdevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long userId;
    private final String username;
    private final Long scheduleId;
    private final String title;
    private final String contents;
    private final Long commentId;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponse(Long userId, String username, Long scheduleId, String title, String contents, Long commentId, String comment, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.username = username;
        this.scheduleId = scheduleId;
        this.title = title;
        this.contents = contents;
        this.commentId = commentId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
