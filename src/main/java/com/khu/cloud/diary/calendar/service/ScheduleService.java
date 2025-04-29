package com.khu.cloud.diary.calendar.service;

import com.khu.cloud.diary.calendar.dto.ScheduleRequest;
import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.repository.ScheduleRepository;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    public Schedule saveSchedule(ScheduleRequest scheduleRequest){
        //String token = resolveTokenFromRequest(request);
        Schedule newSchedule = new Schedule();

        newSchedule.setContent(scheduleRequest.getContent());
        newSchedule.setDate(scheduleRequest.getDate());
        newSchedule.setEmotion_icon(scheduleRequest.getEmotionIcon());
        newSchedule.setUserId(scheduleRequest.getUserId());
        return scheduleRepository.save(newSchedule);
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

    public Schedule editSchedule(ScheduleRequest scheduleRequest){
        Schedule schedule;
        Long scheduleId = scheduleRequest.getScheduleId();
        if (scheduleRepository.findByScheduleId(scheduleId).isPresent())
            schedule = scheduleRepository.findByScheduleId(scheduleId).get();
        else
            return null;

        schedule.updateSchedule(scheduleRequest.getDate(), scheduleRequest.getContent(),
                                scheduleRequest.getEmotionIcon());
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId){
        scheduleRepository.deleteById(scheduleId);
    }
}
