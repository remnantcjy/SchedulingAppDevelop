package org.example.schedulingappdevelop.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        // 일정 아이디

    private String author;      // 작성 유저명
    private String title;       // 일정 제목
    private String contents;    // 일정 내용

    public Schedule(String author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
