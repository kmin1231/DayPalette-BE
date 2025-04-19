package com.khu.cloud.diary.calendar.service;

import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule saveSchedule(Long userId, Date date, String content, String emotion_icon){
        return scheduleRepository.save(new Schedule(userId, date, content, emotion_icon));
    }

    public List<Schedule> getAllSchedule(){
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long scheduleId){
        return scheduleRepository.findByScheduleId(scheduleId);
    }

    public List<Schedule> getScheduleByDate(Date date){
        return scheduleRepository.findByDate(date);
    }

    public Schedule editSchedule(Long scheduleId, Date date, String content, String emotion_icon){
        Schedule schedule;
        if (scheduleRepository.findByScheduleId(scheduleId).isPresent())
            schedule = scheduleRepository.findByScheduleId(scheduleId).get();
        else
            return null;

        schedule.updateSchedule(date, content, emotion_icon);
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }
}
