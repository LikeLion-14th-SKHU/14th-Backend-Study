package com.study.minilogjpa.util;

import com.study.minilogjpa.dto.ArticleResponseDto;
import com.study.minilogjpa.dto.FollowResponseDto;
import com.study.minilogjpa.dto.UserResponseDto;
import com.study.minilogjpa.graphql.response.ArticleResponse;
import com.study.minilogjpa.graphql.response.FollowResponse;
import com.study.minilogjpa.graphql.response.UserResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DtoGraphqlMapper {
    public static ArticleResponse toGraphql(ArticleResponseDto article) {
        OffsetDateTime createAt = article.getCreatedAt().atOffset(ZoneOffset.UTC);

        return ArticleResponse.builder()
                .articleId(article.getArticleId())
                .content(article.getContent())
                .authorId(article.getAuthorId())
                .authorName(article.getAuthorName())
                .createdAt(createAt)
                .build();
    }

    public static FollowResponse toGraphql(FollowResponseDto follow) {
        return FollowResponse.builder()
                .followerId(follow.getFollowerId())
                .followeeId(follow.getFolloweeId())
                .build();
    }

    public static UserResponse toGraphql(UserResponseDto user) {
        return UserResponse.builder().id(user.getId()).username(user.getUsername()).build();
    }
}
