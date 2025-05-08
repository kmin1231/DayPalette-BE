package com.khu.cloud.diary.calendar.service;

import com.khu.cloud.diary.calendar.dto.ScheduleRequest;
import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.calendar.repository.ScheduleRepository;
import com.khu.cloud.diary.core.exception.CoreException;
import com.khu.cloud.diary.core.exception.ExceptionType;
import com.khu.cloud.diary.member.entity.Member;
import com.khu.cloud.diary.member.repository.MemberRepository;
import com.khu.cloud.diary.member.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.khu.cloud.diary.posts.util.AuthUtil.resolveTokenFromRequest;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;

    public Schedule saveSchedule(ScheduleRequest scheduleRequest){
        String email = extractEmailFromJwt();

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CoreException(ExceptionType.USER_NOT_FOUND) );


        Schedule newSchedule = Schedule.builder().
                content(scheduleRequest.getContent()).
                userId(member.getUserId()).
                date(scheduleRequest.getDate()).
                emotion_icon(scheduleRequest.getEmotionIcon()).build();

        return scheduleRepository.save(newSchedule);
    }

    public List<Schedule> getAllScheduleByUserId(){
        String token = resolveTokenFromRequest(request);
        String email = jwtUtil.extractEmail(token);

        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new CoreException(ExceptionType.USER_NOT_FOUND) );
        return scheduleRepository.findByUserId(member.getUserId());
    }

    public Optional<Schedule> getScheduleById(Long scheduleId){
        return Optional.ofNullable(scheduleRepository.findByScheduleId(scheduleId)
                .orElseThrow(() -> new CoreException(ExceptionType.SCHEDULE_NOT_FOUND)));
    }

    public List<Schedule> getScheduleByDate(Date date){
        return scheduleRepository.findByDate(date);
    }

    public Schedule editSchedule(Long scheduleId, ScheduleRequest scheduleRequest){
        Schedule schedule;
        schedule = scheduleRepository.findByScheduleId(scheduleId)
                .orElseThrow( () -> new CoreException(ExceptionType.SCHEDULE_NOT_FOUND));

        schedule.updateSchedule(scheduleRequest.getDate(), scheduleRequest.getContent(),
                                scheduleRequest.getEmotionIcon());
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findByScheduleId(scheduleId)
                .orElseThrow( () -> new CoreException(ExceptionType.SCHEDULE_NOT_FOUND));
        scheduleRepository.delete(schedule);
    }

    private String extractEmailFromJwt() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return jwtUtil.extractEmail(token);
    }
}
