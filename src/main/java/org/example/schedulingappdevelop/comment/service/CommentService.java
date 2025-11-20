package org.example.schedulingappdevelop.comment.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.comment.dto.CommentResponse;
import org.example.schedulingappdevelop.comment.dto.CreateCommentRequest;
import org.example.schedulingappdevelop.comment.dto.DeleteCommentRequest;
import org.example.schedulingappdevelop.comment.dto.UpdateCommentRequest;
import org.example.schedulingappdevelop.comment.entity.Comment;
import org.example.schedulingappdevelop.comment.repository.CommentRepository;
import org.example.schedulingappdevelop.common.config.Exception.*;
import org.example.schedulingappdevelop.common.config.auth.PasswordEncoder;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.schedule.repository.ScheduleRepository;
import org.example.schedulingappdevelop.user.dto.SessionUser;
import org.example.schedulingappdevelop.user.entity.User;
import org.example.schedulingappdevelop.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 댓글 생성
     * @param sessionUser
     * @param scheduleId
     * @param request
     * @return
     */
    @Transactional
    public CommentResponse save(SessionUser sessionUser, Long scheduleId, CreateCommentRequest request) {

        // 해당 일정 id가 존재하는지 확인 / 예외처리
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 일정이 없습니다.")
        );

        // 해당 일정의 User 반환
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new UserNotFoundException("유저가 없습니다.")
        );

        // 댓글 생성
        Comment comment = new Comment(request.getComment(), user, schedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(
                savedComment.getUser().getId(),
                savedComment.getSchedule().getUser().getName(),
                savedComment.getSchedule().getId(),
                savedComment.getSchedule().getTitle(),
                savedComment.getSchedule().getContents(),
                savedComment.getId(),
                savedComment.getUser().getName(),
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
                    comment.getSchedule().getUser().getName(),
                    comment.getSchedule().getId(),
                    comment.getSchedule().getTitle(),
                    comment.getSchedule().getContents(),
                    comment.getId(),
                    comment.getUser().getName(),
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
                    comment.getSchedule().getUser().getName(),
                    comment.getSchedule().getId(),
                    comment.getSchedule().getTitle(),
                    comment.getSchedule().getContents(),
                    comment.getId(),
                    comment.getUser().getName(),
                    comment.getComment(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }


    /**
     * 댓글 수정
     * @param scheduleId
     * @param commentId
     * @param request
     * @return
     */
    public CommentResponse update(SessionUser sessionUser, Long scheduleId, Long commentId, @Valid UpdateCommentRequest request) {
        // 해당 일정이 있는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 일정이 없습니다.")
        );

        // 해당 댓글이 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 없습니다.")
        );


        // 해당 schedule의 이메일과 request.email이 같은지 확인
        if (!sessionUser.getEmail().equals(comment.getUser().getEmail())) {
            throw new OwnerMismatchException("당사자만 수정 가능합니다.");
        }

        // 비밀번호 검증
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), comment.getUser().getPassword());

        // 비밀번호 불일치 시, 예외 처리
        if (!passwordMatches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않으므로 댓글을 수정할 수 없습니다.");
        }

        // 댓글 수정
        comment.update(request.getComment());

        // 변경된 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        // 반환
        return new CommentResponse(
                savedComment.getUser().getId(),
                savedComment.getSchedule().getUser().getName(),
                savedComment.getSchedule().getId(),
                savedComment.getSchedule().getTitle(),
                savedComment.getSchedule().getContents(),
                savedComment.getId(),
                savedComment.getUser().getName(),
                savedComment.getComment(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }


    /**
     * 댓글 삭제
     * @param sessionUser
     * @param scheduleId
     * @param commentId
     */
    @Transactional
    public void delete(SessionUser sessionUser, DeleteCommentRequest request, Long scheduleId, Long commentId) {

        // 해당 일정이 있는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("해당 일정이 없습니다.")
        );

        // 해당 댓글이 있는지 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("해당 댓글이 없습니다.")
        );

        // 해당 schedule의 이메일과 request.email이 같은지 확인
        if (!sessionUser.getEmail().equals(comment.getUser().getEmail())) {
            throw new OwnerMismatchException("당사자만 삭제 가능합니다.");
        }

        // 비밀번호 검증
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), schedule.getUser().getPassword());

        // 비밀번호 불일치 시, 예외 처리
        if (!passwordMatches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않으므로 댓글을 삭제할 수 없습니다.");
        }

        // 댓글 삭제
        commentRepository.deleteById(commentId);
    }
}
