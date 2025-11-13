package org.example.schedulingappdevelop.schedule.controller;

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
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody CreateScheduleRequest request
    ) {
        CreateScheduleResponse result = scheduleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Lv 1. 일정 조회 - Read
    // 다건 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getSchedule() {

        List<GetScheduleResponse> result = scheduleService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 단건 조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long id)
    {
        GetScheduleResponse result = scheduleService.getOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Lv 1. 일정 수정 - Update
    @PutMapping("/schedules")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @RequestBody UpdateScheduleRequest request
    ) {

        UpdateScheduleResponse result = scheduleService.update(request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Lv 1. 일정 삭제 - Delete
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id
    ) {

        scheduleService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
