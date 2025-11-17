package org.example.schedulingappdevelop.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.comment.dto.CommentResponse;
import org.example.schedulingappdevelop.comment.dto.CreateCommentRequest;
import org.example.schedulingappdevelop.comment.entity.Comment;
import org.example.schedulingappdevelop.comment.repository.CommentRepository;
import org.example.schedulingappdevelop.common.config.Exception.ScheduleNotFoundException;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.schedule.repository.ScheduleRepository;
import org.example.schedulingappdevelop.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     * @param scheduleId
     * @param request
     * @return
     */
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

    /**
     * 댓글 (단건) 조회
     * @param scheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getOne(Long scheduleId) {

        // 일정 id가 있을 때
        // 일정 id에 해당하는 댓글들을 리스트로 받아 응답 객체로 반환 - 수정일 기준 내림차순 정렬
        List<Comment> commentList = commentRepository.findByScheduleIdOrderByModifiedAtDesc(scheduleId);

        List<CommentResponse> dtos = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentResponse dto = new CommentResponse(
                    comment.getUser().getId(),
                    comment.getUser().getName(),
                    comment.getSchedule().getId(),
                    comment.getSchedule().getTitle(),
                    comment.getSchedule().getContents(),
                    comment.getId(),
                    comment.getComment(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }


    /**
     * 댓글 (다건) 조회
     * @return
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getAll() {

        // 일정 id가 없을 때
        // 일정 저장소에 있는 댓글 리스트로 받아 응답 객체로 반환 - 수정일 기준 내림차순 정렬
        List<Comment> commentList = commentRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));

        List<CommentResponse> dtos = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentResponse dto = new CommentResponse(
                    comment.getUser().getId(),
                    comment.getUser().getName(),
                    comment.getSchedule().getId(),
                    comment.getSchedule().getTitle(),
                    comment.getSchedule().getContents(),
                    comment.getId(),
                    comment.getComment(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
