package com.khu.cloud.diary.community.init;

import com.khu.cloud.diary.community.entity.DiaryPost.DiaryPost;
import com.khu.cloud.diary.community.entity.Users.Users;
import com.khu.cloud.diary.community.repository.DiaryPostRepository;
import com.khu.cloud.diary.community.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final UserRepository userRepository;
    private final DiaryPostRepository diaryPostRepository;

    @PostConstruct
    public void init() {
        // 테스트용 유저 생성
        Users user = userRepository.save(
                Users.builder()
                        .email("test@email.com")
                        .password("1234")
                        .nickname("테스트유저")
                        .build()
        );

        // 테스트용 일기 생성
        diaryPostRepository.save(
                DiaryPost.builder()
                        .user(user)
                        .content("오늘은 기분이 좋았어요!")
                        .emotionTag("기쁨")
                        .isShared(true)
                        .build()
        );
    }
}