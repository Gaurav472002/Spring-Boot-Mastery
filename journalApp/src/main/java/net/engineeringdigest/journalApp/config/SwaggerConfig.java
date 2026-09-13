package net.engineeringdigest.journalApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {

        Server devServer = new Server()
                .url("http://localhost:8085/dev/journal")
                .description("Local Development Server");

        Server prodServer = new Server()
                .url("http://localhost:8086/prod/journal")
                .description("Production Server");

        return new OpenAPI()
                .info(new Info()
                        .title("Journal App APIs")
                        .description("REST APIs for Journal Application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Gaurav")))
                .servers(Arrays.asList(devServer, prodServer))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}