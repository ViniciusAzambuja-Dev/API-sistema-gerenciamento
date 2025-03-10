package com.vinicius.sistema_gerenciamento.infra.seguranca.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
 public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()  
                .info(new Info()
                    .title("WorkFlow API Documentation")
                    .version("1.0")
                    .description("Documentação da API para o projeto final do Trainee"))


                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes("Bearer Authentication",
                        new SecurityScheme()
                            .name("Bearer Authentication")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
                );
    }
 }