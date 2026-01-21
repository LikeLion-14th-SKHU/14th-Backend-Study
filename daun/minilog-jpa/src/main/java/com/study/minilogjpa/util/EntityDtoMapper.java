package com.study.minilogjpa.util;

import com.study.minilogjpa.dto.ArticleResponseDto;
import com.study.minilogjpa.dto.FollowResponseDto;
import com.study.minilogjpa.dto.UserResponseDto;
import com.study.minilogjpa.entity.Article;
import com.study.minilogjpa.entity.Follow;
import com.study.minilogjpa.entity.User;

public class EntityDtoMapper {
    public static ArticleResponseDto toDto(Article article) {
        return ArticleResponseDto.builder()
                .articleId(article.getId())
                .content(article.getContent())
                .authorId(article.getAuthor().getId())
                .authorName(article.getAuthor().getUsername())
                .createdAt(article.getCreatedAt())
                .build();
    }

    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .followerId(follow.getFollower().getId())
                .followeeId(follow.getFollowee().getId())
                .build();
    }

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder().id(user.getId()).username(user.getUsername()).build();
    }

    public static Follow toEntity(Long followerId, Long followeeId) {
        return Follow.builder()
                .follower(User.builder().id(followerId).build())
                .followee(User.builder().id(followeeId).build())
                .build();
    }
}
