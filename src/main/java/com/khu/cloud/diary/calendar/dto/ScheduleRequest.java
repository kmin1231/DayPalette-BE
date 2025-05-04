package com.khu.cloud.diary.calendar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScheduleRequest {
    private Date date;
    private String Content;
    private String emotionIcon;
}
