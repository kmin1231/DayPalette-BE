package com.khu.cloud.diary.calendar.controller;

import com.khu.cloud.diary.calendar.dto.PostAndScheduleResponse;
import com.khu.cloud.diary.calendar.dto.ScheduleRequest;
import com.khu.cloud.diary.calendar.dto.ScheduleResponse;
import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.service.ScheduleService;
import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.posts.dto.PostMineResponse;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.posts.service.PostMineService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final PostMineService postService;

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules(){
        List<Schedule> schedules = scheduleService.getAllScheduleByUserId();
        List<ScheduleResponse> response = schedules.stream()
                        .map(schedule-> ScheduleResponse.builder()
                            .scheduleId(schedule.getScheduleId())
                            .date(schedule.getDate())
                            .time(schedule.getTime())
                            .content(schedule.getContent())
                            .build())
                        .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{date}")
    public ResponseEntity<PostAndScheduleResponse> getScheduleAndPost(@PathVariable String date) {
        /*
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new CoreException(ExceptionType.INVALID_DATE_FORMAT);
        }

        Date convertedDate = java.sql.Date.valueOf(localDate);
         */
        List<Schedule> schedules = scheduleService.getScheduleByDate(date);
        List<Post> posts = postService.getMyPostsByDate(date);

        PostAndScheduleResponse response = new PostAndScheduleResponse(schedules, posts);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/schedule/add")
    public ResponseEntity<ScheduleResponse> addSchedule(@RequestBody ScheduleRequest scheduleRequest){
        Schedule newSchedule = scheduleService.saveSchedule(scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                newSchedule.getScheduleId(),
                newSchedule.getContent(),
                newSchedule.getDate(),
                newSchedule.getTime(),
                // newSchedule.getEmotion_icon(),
                newSchedule.getCreatedAt() );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/schedule/delete/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long scheduleId, HttpServletRequest request){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body("successfully delete");
    }

    @PutMapping("/schedule/edit/{scheduleId}")
    public ResponseEntity<ScheduleResponse> editSchedule(@RequestBody ScheduleRequest scheduleRequest,
                                                         @PathVariable Long scheduleId,
                                                         HttpServletRequest request){
        Schedule schedule = scheduleService.editSchedule(scheduleId, scheduleRequest);
        ScheduleResponse response = new ScheduleResponse(
                schedule.getScheduleId(),
                schedule.getContent(),
                schedule.getDate(),
                schedule.getTime(),
                // schedule.getEmotion_icon(),
                schedule.getCreatedAt());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}