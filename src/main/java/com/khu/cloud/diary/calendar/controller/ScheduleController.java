package com.khu.cloud.diary.calendar.controller;

import com.khu.cloud.diary.calendar.dto.ScheduleRequest;
import com.khu.cloud.diary.calendar.dto.ScheduleResponse;
import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ScheduleResponse> add(@RequestBody ScheduleRequest scheduleRequest){
        Schedule newSchedule = scheduleService.saveSchedule(scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                newSchedule.getScheduleId(), newSchedule.getContent(),
                newSchedule.getDate(),       newSchedule.getEmotion_icon() );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody ScheduleRequest scheduleRequest){
        scheduleService.deleteSchedule(scheduleRequest.getScheduleId());
    }

    @PutMapping("/edit")
    public ResponseEntity<ScheduleResponse> edit(@RequestBody ScheduleRequest scheduleRequest){
        Schedule schedule = scheduleService.editSchedule(scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                schedule.getScheduleId(), schedule.getContent(),
                schedule.getDate(),       schedule.getEmotion_icon() );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
