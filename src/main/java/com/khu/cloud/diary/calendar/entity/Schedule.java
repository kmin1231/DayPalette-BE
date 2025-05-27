package com.khu.cloud.diary.calendar.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private Long userId;

    // "yyyy-mm-dd"
    @Column(nullable = false)
    private String date;

    // "hh-mm"
    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String content;

    // @Column(nullable = true)
    // private String emotion_icon;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Schedule(Long userId, String date, String time, String content){
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.content = content;
        // this.emotion_icon = emotion_icon;
    }

    public void updateSchedule(String date, String time, String content){
        this.date = date;
        this.time = time;
        this.content = content;
        // this.emotion_icon = emotion_icon;
    }
    
}