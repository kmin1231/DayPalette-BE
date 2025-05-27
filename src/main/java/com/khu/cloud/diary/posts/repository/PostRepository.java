// posts/repository/PostRepository.java

package com.khu.cloud.diary.posts.repository;

import com.khu.cloud.diary.posts.entity.Post;
import com.khu.cloud.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(Member user);
    List<Post> findByUserAndDate(Member user, String date);

    // 최신순 (전체)
    List<Post> findTop10ByIsSharedTrueOrderByCreatedAtDesc();

    // 최신순 (감정별)
    // List<Post> findTop10ByIsSharedTrueAndEmojiOrderByCreatedAtDesc(String emoji);

    // 좋아요순 (전체)
    List<Post> findTop10ByIsSharedTrueOrderByLikeCountDescCreatedAtDesc();

    // 좋아요순 (감정별)
    // List<Post> findTop10ByIsSharedTrueAndEmojiOrderByLikeCountDescCreatedAtDesc(String emoji);
}