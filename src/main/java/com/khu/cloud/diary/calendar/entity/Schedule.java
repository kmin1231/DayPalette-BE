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

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String emotion_icon;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

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