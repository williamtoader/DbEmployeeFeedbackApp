package com.db.cloudschool.employeefeedback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.HttpAuthenticationBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        // Creates OpenAPI Standard 3.0 documentation endpoint /v3/**
        return new Docket(DocumentationType.OAS_30)
                .servers(
                        new Server(
                                "api-dev",
                                "http://35.230.104.77:80",
                                "Development server",
                                List.of(),
                                List.of()
                        )
                )
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("bearer-jwt", authorizationScopes));
    }

    private SecurityScheme apiKey() {
        return new HttpAuthenticationBuilder()
                .name("bearer-jwt")
                .scheme("bearer")
                .bearerFormat("JWT")
                .build();
    }
}
