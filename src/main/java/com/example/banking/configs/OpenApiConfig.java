package com.example.banking.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Banking Demo API",
                version = "1.0",
                description = "REST API для управления пользователями и аккаунтами"
        )
)
public class OpenApiConfig {
}