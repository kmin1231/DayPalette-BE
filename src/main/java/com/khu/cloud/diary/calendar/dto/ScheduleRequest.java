package com.khu.cloud.diary.calendar.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
public class ScheduleRequest {
    @Nullable
    private Long scheduleId;

    private Long userId;
    private Date date;
    private String Content;
    private String emotionIcon;
}
