package com.khu.cloud.diary.calendar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
    private String date;
    private String time;
    private String content;
    // private String emotionIcon;
}