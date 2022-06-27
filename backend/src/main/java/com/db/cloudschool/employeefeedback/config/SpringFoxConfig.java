package com.db.cloudschool.employeefeedback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
