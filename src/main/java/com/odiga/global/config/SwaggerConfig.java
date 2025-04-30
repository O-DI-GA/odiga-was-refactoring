package com.odiga.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "O-DI-GA",
        description = "O-DI-GA API 명세서.",
        version = "v1")
)
public class SwaggerConfig {

    static {
        SpringDocUtils config = SpringDocUtils.getConfig();
        config.replaceWithClass(LocalDate.class, String.class);
        config.replaceWithClass(LocalTime.class, String.class);
        config.replaceWithClass(LocalDateTime.class, String.class);
    }

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/api/v1/**"};

        return GroupedOpenApi.builder()
            .group("O-DI-GA v1")
            .pathsToMatch(paths)
            .build();
    }

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER).name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .security(Arrays.asList(securityRequirement));

    }

}