package org.example.schedulingappdevelop.schedule.repository;

import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
