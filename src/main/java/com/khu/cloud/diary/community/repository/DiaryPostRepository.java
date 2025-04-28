package com.khu.cloud.diary.community.repository;

import com.khu.cloud.diary.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryPostRepository extends JpaRepository<Post, Long> {
    // 최신순 (전체)
    List<Post> findTop10ByIsSharedTrueOrderByCreatedAtDesc();

    // 최신순 (감정별)
    List<Post> findTop10ByIsSharedTrueAndEmojiOrderByCreatedAtDesc(String emoji);

    // 좋아요순 (전체)
    List<Post> findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc();

    // 좋아요순 (감정별)
    List<Post> findTop10ByIsSharedTrueAndEmojiOrderByLikeCountDescCreatedAtDesc(String emoji);
}
