package uz.carapp.rentcarapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "RENT CAR  API", version = "v1", description = "${api.description}"), servers = @Server(url = "${server.base.url}"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenAPI30Configuration {

    @Bean
    public GroupedOpenApi storeOpenApi() {
        String[] path = {
                "/api/account/auth",
                "/api/account/me",
                "/api/account/profile",
                "/api/account/change-password",
                "/api/v1/merchants/**"
        };
        return GroupedOpenApi.builder().group("LMS Application").pathsToMatch(path).build();
    }
}
