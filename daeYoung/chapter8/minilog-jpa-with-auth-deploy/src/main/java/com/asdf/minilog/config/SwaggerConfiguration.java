package com.asdf.minilog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {


    //swagger v3로 가면서 애노테이션 변경이 있다.

    @Value("${swagger.server-url}")
    private String serverUrl;


    @Bean
    public OpenAPI swaggerConfig(){

        Info info = new Info()
                .title("Swagger Test App")
                .description("description : 스프링 스웨거 설명을 위한 앱 입니다.")
                .version("7.7")
                .contact(new Contact()
                        .name("담당자 이름 : 장대영")
                        .email("담당자이메일@naver.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"));


        //외부 문서 링크 (옵션)
        ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("God Tong Wiki Documentation")
                .url("https://www.godtong.com/docs");

//        Components components = new Components()
//                .addSecuritySchemes();


        return new OpenAPI()
                .info(info)
                .servers(List.of(new Server().url(serverUrl)))
                .externalDocs(externalDocs)
                .components(new Components().addSecuritySchemes("beare-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));







    }




}
