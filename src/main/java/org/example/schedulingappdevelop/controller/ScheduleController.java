package org.example.schedulingappdevelop.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedulingappdevelop.dto.CreateScheduleRequest;
import org.example.schedulingappdevelop.dto.CreateScheduleResponse;
import org.example.schedulingappdevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody CreateScheduleRequest request
    ) {
        CreateScheduleResponse result = scheduleService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
