package com.study.minilogjpa.repository;

import com.study.minilogjpa.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long followerId);

    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
