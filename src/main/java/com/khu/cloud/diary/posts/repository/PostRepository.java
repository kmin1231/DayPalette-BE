// posts/repository/PostRepository.java

package com.khu.cloud.diary.posts.repository;

import com.khu.cloud.diary.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}