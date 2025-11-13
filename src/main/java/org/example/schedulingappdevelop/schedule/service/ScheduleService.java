package org.example.schedulingappdevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.schedule.dto.*;
import org.example.schedulingappdevelop.schedule.entity.Schedule;
import org.example.schedulingappdevelop.schedule.repository.ScheduleRepository;
import org.springframework.data.domain.Sort;
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

        // 레포지토리에서 모든 일정을 찾아서 일정리스트로 반환 - 수정일 기준 내림차순
        List<Schedule> scheduleList = scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));

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

    // Lv 1. 일정 수정 - Update
    @Transactional
    public UpdateScheduleResponse update(UpdateScheduleRequest request) {

        // 해당 id의 일정이 있는지 확인
        Long scheduleId = request.getId();

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        // 일정 수정
        schedule.update(request.getTitle(), request.getContents());

        // 응답 객체로 변환 후 반환
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getAuthor(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // Lv 1. 일정 삭제 - Delete
    @Transactional
    public void delete(Long scheduleId) {
        // 해당 id의 일정이 있는지 확인
        boolean existence = scheduleRepository.existsById(scheduleId);

        // 해당 id의 일정이 없으면 예외처리
        if (!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        } else {
            // 있으면 삭제
            scheduleRepository.deleteById(scheduleId);
        }

    }
}
