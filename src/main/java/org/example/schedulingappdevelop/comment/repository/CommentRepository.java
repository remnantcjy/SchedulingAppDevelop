package org.example.schedulingappdevelop.comment.repository;

import org.example.schedulingappdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleIdOrderByModifiedAtDesc(Long scheduleId);
}
