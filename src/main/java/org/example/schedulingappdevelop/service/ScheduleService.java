package org.example.schedulingappdevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.dto.CreateScheduleRequest;
import org.example.schedulingappdevelop.dto.CreateScheduleResponse;
import org.example.schedulingappdevelop.dto.GetScheduleResponse;
import org.example.schedulingappdevelop.entity.Schedule;
import org.example.schedulingappdevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // Lv1. 일정 생성 - Create
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

    // Lv 1. 일정 조회 - Read
    // 다건 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {

        // 레포지토리에서 모든 일정을 찾아서 일정리스트로 반환
        List<Schedule> scheduleList = scheduleRepository.findAll();

        // 응답 객체로 만들어주기 위한 dtos 리스트 생성
        List<GetScheduleResponse> dtos = new ArrayList<>();

        // 응답 객체 변환 후, dtos 리스트에 추가
        for (Schedule schedule : scheduleList) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getAuthor(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );

            dtos.add(dto);
        }

        return dtos;
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long id) {

        // id에 해당하는 일정이 있는지 확인 / 예외처리
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getAuthor(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );

    }
}
