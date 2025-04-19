package com.khu.cloud.diary.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String emotion_icon;

    public Schedule(Long userId, Date date, String content, String emotion_icon){
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.emotion_icon = emotion_icon;
    }

    public void updateSchedule(Date date, String content, String emotion_icon){
        this.date = date;
        this.content = content;
        this.emotion_icon = emotion_icon;
    }
    
}