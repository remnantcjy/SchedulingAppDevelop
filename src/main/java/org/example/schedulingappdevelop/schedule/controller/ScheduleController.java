package org.example.schedulingappdevelop.schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.schedule.dto.*;
import org.example.schedulingappdevelop.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // Lv 1. 일정 생성 - Create
    @PostMapping("/schedules/{userId}")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @PathVariable Long userId,
            @Valid @RequestBody CreateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(userId, request));
    }


    // Lv 1. 일정 조회 - Read
    // 다건 조회 - 쿼리 파라미터 값이 null 일 때
    // 단건 조회 - 쿼리 파라미터 값이 존재할 때
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getSchedule(
            @RequestParam(required = false) Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userId));
    }


    // Lv 1. 일정 수정 - Update
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }


    // Lv 1. 일정 삭제 - Delete
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId
    ) {

        scheduleService.delete(scheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
