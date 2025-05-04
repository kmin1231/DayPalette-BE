package com.khu.cloud.diary.calendar.dto;

import com.khu.cloud.diary.calendar.entity.Schedule;
import com.khu.cloud.diary.posts.dto.PostMineResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostAndScheduleResponse {
    List<Schedule> schedules;
    List<PostMineResponse> posts;

    public PostAndScheduleResponse(List<Schedule> schedules, List<PostMineResponse> posts){
        this.schedules = schedules;
        this.posts = posts;
    }
}
