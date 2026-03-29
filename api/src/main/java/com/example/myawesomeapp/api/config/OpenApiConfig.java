package com.example.myawesomeapp.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Awesome App API")
                        .description("REST API for managing users and products")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("My Awesome App Team")
                                .email("team@example.com")));
    }
}
