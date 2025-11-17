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
            HttpSession session
    ) {
        // 1. 로그인 확인
        SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new LoginRequiredException("댓글을 작성하기 위해 로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
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
            HttpSession session
    ) {
        // 로그인 시 수정 가능
        SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new LoginRequiredException("댓글을 수정하기 위해 로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(loginUser, scheduleId, commentId, request));
    }

    // 댓글 삭제
    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommentRequest request,
            HttpSession session
    ) {
        // 로그인 시 삭제 가능
        SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new LoginRequiredException("댓글을 삭제하기 위해 로그인이 필요합니다.");
        }

        commentService.delete(loginUser, request, scheduleId, commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
