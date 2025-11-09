package org.example.schedulingappdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.dto.CreateScheduleRequest;
import org.example.schedulingappdevelop.dto.CreateScheduleResponse;
import org.example.schedulingappdevelop.entity.Schedule;
import org.example.schedulingappdevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {

        // request에서 값 꺼내와 일정 객체로 변환
        Schedule schedule = new Schedule(
                request.getAuthor(),
                request.getTitle(),
                request.getContents()
        );

        // 레포지토리에 일정 저장
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 저장된 데이터 - 응답 DTO로 반환
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getAuthor(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }
}
