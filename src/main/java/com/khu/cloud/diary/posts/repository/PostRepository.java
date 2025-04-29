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
}