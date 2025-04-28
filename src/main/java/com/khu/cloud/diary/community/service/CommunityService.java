package com.khu.cloud.diary.community.service;

import com.khu.cloud.diary.community.dto.DiaryPostFeedResponseDto;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.community.repository.DiaryPostRepository;
import com.khu.cloud.diary.community.type.FeedSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final DiaryPostRepository diaryPostRepository;

    public List<DiaryPostFeedResponseDto> getFeed(FeedSortType sort, String emotion) {
        List<Post> posts;

        boolean isLikeSort = FeedSortType.LIKE.equals(sort);
        boolean hasEmotion = (emotion != null && !emotion.isBlank());

        if (isLikeSort) {
            posts = hasEmotion
                    ? diaryPostRepository.findTop10ByIsSharedTrueAndEmojiOrderByLikeCountDescCreatedAtDesc(emotion)
                    : diaryPostRepository.findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc();
        } else { // default: 최신순
            posts = hasEmotion
                    ? diaryPostRepository.findTop10ByIsSharedTrueAndEmojiOrderByCreatedAtDesc(emotion)
                    : diaryPostRepository.findTop10ByIsSharedTrueOrderByCreatedAtDesc();
        }

        return posts.stream()
                .map(DiaryPostFeedResponseDto::new)
                .collect(Collectors.toList());
    }
}
