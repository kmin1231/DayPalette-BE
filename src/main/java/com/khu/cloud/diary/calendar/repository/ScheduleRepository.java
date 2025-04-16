package com.khu.cloud.diary.calendar.repository;

import com.khu.cloud.diary.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
    Optional<Schedule> findByScheduleId(Long scheduleId);
}