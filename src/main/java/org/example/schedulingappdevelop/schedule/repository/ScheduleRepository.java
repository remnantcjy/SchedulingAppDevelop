package org.example.schedulingappdevelop.schedule.repository;

import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Long user(User user);

    List<Schedule> findByUserIdOrderByModifiedAtDesc(Long userId);

    List<Schedule> findByUserId(Long userId);
}
