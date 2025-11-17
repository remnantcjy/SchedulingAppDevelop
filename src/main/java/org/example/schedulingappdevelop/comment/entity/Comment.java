package org.example.schedulingappdevelop.comment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.user.entity.User;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 댓글 id

    private String comment;    // 댓글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(String comment, User user, Schedule schedule) {
        this.comment = comment;
        this.user = user;
        this.schedule = schedule;
    }

    public void update(@NotBlank String comment) {
        this.comment = comment;
    }
}
