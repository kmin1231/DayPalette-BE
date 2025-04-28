package com.khu.cloud.diary.community.repository;

import com.khu.cloud.diary.community.entity.DiaryPost.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryPostRepository extends JpaRepository<DiaryPost, Long> {
    // 최신순 (전체)
    List<DiaryPost> findTop10ByIsSharedTrueOrderByCreatedAtDesc();

    // 최신순 (감정별)
    List<DiaryPost> findTop10ByIsSharedTrueAndEmotionTagOrderByCreatedAtDesc(String emotion);

    // 좋아요순 (전체)
    List<DiaryPost> findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc();

    // 좋아요순 (감정별)
    List<DiaryPost> findTop10ByIsSharedTrueAndEmotionTagOrderByLikeCountDescCreatedAtDesc(String emotion);
}
