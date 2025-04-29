package com.khu.cloud.diary.calendar.controller;

import com.khu.cloud.diary.calendar.dto.ScheduleRequest;
import com.khu.cloud.diary.calendar.dto.ScheduleResponse;
import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<ScheduleResponse>> getScheduleAndPost(@PathVariable Date date, HttpServletRequest request){
        List<Schedule> mySchedules = scheduleService.getScheduleByDate(date);
        List<ScheduleResponse> responses = new java.util.ArrayList<>(List.of());

        for (Schedule tempSchedule : mySchedules) {
            responses.add(new ScheduleResponse(
                    tempSchedule.getScheduleId(), tempSchedule.getContent(),
                    tempSchedule.getDate(), tempSchedule.getEmotion_icon()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/schedule/add")
    public ResponseEntity<ScheduleResponse> addSchedule(@RequestBody ScheduleRequest scheduleRequest){
        Schedule newSchedule = scheduleService.saveSchedule(scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                newSchedule.getScheduleId(), newSchedule.getContent(),
                newSchedule.getDate(),       newSchedule.getEmotion_icon() );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/schedule/delete")
    public void deleteSchedule(@RequestBody ScheduleRequest scheduleRequest){
        scheduleService.deleteSchedule(scheduleRequest.getScheduleId());
    }

    @PutMapping("/schedule/edit")
    public ResponseEntity<ScheduleResponse> editSchedule(@RequestBody ScheduleRequest scheduleRequest){
        Schedule schedule = scheduleService.editSchedule(scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                schedule.getScheduleId(), schedule.getContent(),
                schedule.getDate(),       schedule.getEmotion_icon() );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
