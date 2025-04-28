package com.khu.cloud.diary.community.repository;

import com.khu.cloud.diary.community.entity.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

}
