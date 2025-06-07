package com.khu.cloud.diary.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class ScheduleResponse {
    private Long scheduleId;
    private String date;
    private String time;
    private String content;
    // private String emotionIcon;
    private LocalDateTime createdAt;

    public ScheduleResponse(Long scheduleId, String date, String time, String content, LocalDateTime createdAt){
        this.scheduleId = scheduleId;
        this.content = content;
        this.date = date;
        this.time = time;
        // this.emotionIcon = emotionIcon;
        this.createdAt = createdAt;
    }
}
