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

    @Id
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String emotion_icon;
    
}