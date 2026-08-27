package com.project.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple Restaurant API")
                        .description("RESTful API for restaurant management - covering menus, customers, and purchase transactions.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ki")
                                .url("https://github.com/Riskihidayatt")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Local Development")));
    }
}
