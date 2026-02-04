package com.study.minilogjpa.graphql;

import com.study.minilogjpa.dto.UserRequestDto;
import com.study.minilogjpa.graphql.input.CreateArticleInput;
import com.study.minilogjpa.graphql.input.CreateUserInput;
import com.study.minilogjpa.graphql.input.UpdateArticleInput;
import com.study.minilogjpa.graphql.input.UpdateUserInput;
import com.study.minilogjpa.graphql.response.ArticleResponse;
import com.study.minilogjpa.graphql.response.FollowResponse;
import com.study.minilogjpa.graphql.response.UserResponse;
import com.study.minilogjpa.security.MinilogUserDetails;
import com.study.minilogjpa.service.ArticleService;
import com.study.minilogjpa.service.FollowService;
import com.study.minilogjpa.service.UserService;
import com.study.minilogjpa.util.DtoGraphqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class GraphQLMutationController {

    private final ArticleService articleService;
    private final FollowService followService;
    private final UserService userService;

    @Autowired
    public GraphQLMutationController(
            ArticleService articleService,
            FollowService followService,
            UserService userService) {
        this.articleService = articleService;
        this.followService = followService;
        this.userService = userService;
    }

    private MinilogUserDetails getCurrentUser() {
        return (MinilogUserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @MutationMapping
    public ArticleResponse createArticle(@Argument CreateArticleInput input) {
        MinilogUserDetails userDetails = getCurrentUser();
        return DtoGraphqlMapper.toGraphql(
                articleService.createArticle(input.getContent(), userDetails.getId()));
    }

    @MutationMapping
    public ArticleResponse updateArticle(@Argument UpdateArticleInput input) {
        MinilogUserDetails userDetails = getCurrentUser();
        return DtoGraphqlMapper.toGraphql(
                articleService.updateArticle(
                        userDetails.getId(), input.getArticleId(), input.getContent()));
    }

    @MutationMapping
    public Boolean deleteArticle(@Argument Long articleId) {
        MinilogUserDetails userDetails = getCurrentUser();
        articleService.deleteArticle(userDetails.getId(), articleId);
        return true;
    }

    @MutationMapping
    public FollowResponse follow(@Argument Long followeeId) {
        MinilogUserDetails userDetails = getCurrentUser();
        return DtoGraphqlMapper.toGraphql(
                followService.follow(userDetails.getId(), followeeId));
    }

    @MutationMapping
    public Boolean unfollow(@Argument Long followeeId) {
        MinilogUserDetails userDetails = getCurrentUser();
        followService.unfollow(userDetails.getId(), followeeId);
        return true;
    }

    @MutationMapping
    public UserResponse createUser(@Argument CreateUserInput input) {
        return DtoGraphqlMapper.toGraphql(
                userService.createUser(
                        UserRequestDto.builder()
                                .username(input.getUsername())
                                .password(input.getPassword())
                                .build()));
    }

    @MutationMapping
    public UserResponse updateUser(@Argument UpdateUserInput input) {
        MinilogUserDetails userDetails = getCurrentUser();
        return DtoGraphqlMapper.toGraphql(
                userService.updateUser(
                        userDetails,
                        input.getUserId(),
                        UserRequestDto.builder()
                                .username(input.getUsername())
                                .password(input.getPassword())
                                .build()));
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long userId) {
        userService.deleteUser(userId);
        return true;
    }
}
