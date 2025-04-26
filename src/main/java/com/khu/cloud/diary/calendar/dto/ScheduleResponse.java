package com.khu.cloud.diary.calendar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScheduleResponse {
    private Long scheduleId;
    private Date date;
    private String content;
    private String emotionIcon;

    public ScheduleResponse(Long scheduleId, String content, Date date, String emotionIcon){
        this.scheduleId = scheduleId;
        this.content = content;
        this.date = date;
        this.emotionIcon = emotionIcon;
    }
}
