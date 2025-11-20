package org.example.schedulingappdevelop.comment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.comment.dto.CommentResponse;
import org.example.schedulingappdevelop.comment.dto.CreateCommentRequest;
import org.example.schedulingappdevelop.comment.dto.DeleteCommentRequest;
import org.example.schedulingappdevelop.comment.dto.UpdateCommentRequest;
import org.example.schedulingappdevelop.comment.service.CommentService;
import org.example.schedulingappdevelop.common.config.Exception.LoginRequiredException;
import org.example.schedulingappdevelop.user.dto.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser, scheduleId, request));
    }

    // 댓글 조회
    // 단건 조회
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponse>> getComment(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOne(scheduleId));
    }

    // 다건 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> getComment(
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll());
    }

    // 댓글 수정
    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(sessionUser, scheduleId, commentId, request));
    }

    // 댓글 삭제
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ) {

        commentService.delete(sessionUser, request, scheduleId, commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
