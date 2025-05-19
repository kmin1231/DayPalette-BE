package com.khu.cloud.diary.calendar.repository;

import com.khu.cloud.diary.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    Optional<Schedule> findByScheduleId(Long scheduleId);
    List<Schedule> findByDate(Date date);
    List<Schedule> findByUserId(Long userId);
    List<Schedule> findByUserIdAndDate(Long userId, Date date);
}