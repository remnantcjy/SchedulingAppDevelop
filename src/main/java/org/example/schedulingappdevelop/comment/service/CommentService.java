package org.example.schedulingappdevelop.comment.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.comment.dto.CommentResponse;
import org.example.schedulingappdevelop.comment.dto.CreateCommentRequest;
import org.example.schedulingappdevelop.comment.entity.Comment;
import org.example.schedulingappdevelop.comment.repository.CommentRepository;
import org.example.schedulingappdevelop.common.config.Exception.ScheduleNotFoundException;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.schedule.repository.ScheduleRepository;
import org.example.schedulingappdevelop.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse save(Long scheduleId, CreateCommentRequest request) {

        // 해당 일정 id가 존재하는지 확인 / 예외처리
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 일정이 없습니다.")
        );

        // 해당 일정의 User 반환
        User user = schedule.getUser();

        // 댓글 생성
        Comment comment = new Comment(request.getComment(), user, schedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(
                savedComment.getUser().getId(),
                savedComment.getUser().getName(),
                savedComment.getSchedule().getId(),
                savedComment.getSchedule().getTitle(),
                savedComment.getSchedule().getContents(),
                savedComment.getId(),
                savedComment.getComment(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }
}
