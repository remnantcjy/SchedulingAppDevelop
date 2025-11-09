package org.example.schedulingappdevelop.repository;

import org.example.schedulingappdevelop.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
