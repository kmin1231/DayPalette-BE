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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.ZoneId;

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
        

        LocalDateTime dateTime;
        try {
            String dateTimeStr = scheduleRequest.getDate() + "T" + scheduleRequest.getTime().replace("-", ":") + ":00";
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new CoreException(ExceptionType.INVALID_DATE_FORMAT);
        }

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());


        Schedule newSchedule = Schedule.builder().
                content(scheduleRequest.getContent()).
                userId(member.getUserId()).
                date(date).
                // emotion_icon(scheduleRequest.getEmotionIcon())
                build();

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


        LocalDateTime dateTime;
        try {
            String dateTimeStr = scheduleRequest.getDate() + "T" + scheduleRequest.getTime().replace("-", ":") + ":00";
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new CoreException(ExceptionType.INVALID_DATE_FORMAT);
        }

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());


        // schedule.updateSchedule(scheduleRequest.getDate(), scheduleRequest.getContent(), scheduleRequest.getEmotionIcon());
        schedule.updateSchedule(date, scheduleRequest.getContent(), null);

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
