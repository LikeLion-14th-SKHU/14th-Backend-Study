package com.study.minilogjpa.graphql.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class FollowResponse {
    @NonNull
    Long followerId;

    @NonNull
    private Long followeeId;
}
