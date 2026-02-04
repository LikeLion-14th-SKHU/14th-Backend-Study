package com.study.minilogjpa.graphql.input;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleInput {
    private String content;
}
