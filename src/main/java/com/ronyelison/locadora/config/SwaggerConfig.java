package com.ronyelison.locadora.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI().info(info());
    }

    private Info info() {
        return new Info()
                .title("Locadora de Jogos")
                .description("API sobre uma locadora de jogos online")
                .version("v1.0")
                .termsOfService("API Terms")
                .license(new License().name("API LICENSE").url("api.com.br"));
    }
}
