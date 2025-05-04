package com.khu.cloud.diary.calendar.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ScheduleResponse {
    private Long scheduleId;
    private Date date;
    private String content;
    private String emotionIcon;
    private LocalDateTime createdAt;

    public ScheduleResponse(Long scheduleId, String content, Date date, String emotionIcon, LocalDateTime createdAt){
        this.scheduleId = scheduleId;
        this.content = content;
        this.date = date;
        this.emotionIcon = emotionIcon;
        this.createdAt = createdAt;
    }
}
