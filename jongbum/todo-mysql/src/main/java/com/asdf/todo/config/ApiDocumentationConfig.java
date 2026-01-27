package com.asdf.todo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {

  @Bean
  public OpenAPI apiDocumentation() {
    return new OpenAPI()
        .info(
            new Info()
                .title("To-Do List API")
                .version("2.0")
                .description("Spring Boot 3를 이용한 To-Do List API 문서"));
  }
}
