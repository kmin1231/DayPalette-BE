package com.khu.cloud.diary.community.service;

import com.khu.cloud.diary.community.dto.FeedResponseDto;
import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.community.type.FeedSortType;
import com.khu.cloud.diary.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final PostRepository postRepo;
/*
    public List<FeedResponseDto> getFeed(FeedSortType sort, String emotion) {
        List<Post> posts;

        boolean isLikeSort = FeedSortType.LIKE.equals(sort);
        boolean hasEmotion = (emotion != null && !emotion.isBlank());

        if (isLikeSort) {
            posts = hasEmotion
                    ? postRepo.findTop10ByIsSharedTrueAndEmojiOrderByLikeCountDescCreatedAtDesc(emotion)
                    : postRepo.findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc();
        } else { // default: 최신순
            posts = hasEmotion
                    ? postRepo.findTop10ByIsSharedTrueAndEmojiOrderByCreatedAtDesc(emotion)
                    : postRepo.findTop10ByIsSharedTrueOrderByCreatedAtDesc();
        }

        return posts.stream()
                .map(FeedResponseDto::new)
                .collect(Collectors.toList());
    }
 */
        public List<FeedResponseDto> getFeed(FeedSortType sort) {

            List<Post> posts = (sort == FeedSortType.LIKE)
                    ? postRepo.findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc()
                    : postRepo.findTop10ByIsSharedTrueOrderByCreatedAtDesc();

            return posts.stream()
                    .map(FeedResponseDto::new)
                    .collect(Collectors.toList());
        }

}
