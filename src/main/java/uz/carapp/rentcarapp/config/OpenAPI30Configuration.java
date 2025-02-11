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
                "/api/v1/merchants/**",
                "/api/v1/merchant-branches/**",
                "/api/v1/brands/**",
                "/api/account/create-user",
                "/api/account/list",
                "/api/attachments/save-file",
                "/api/v1/car-bodies1/**",
                "/api/v1/car-classes1/**",
                "/api/v1/categories1/**",
                "/api/v1/parametrs1/**",
                "/api/v1/vehicles1/**"

        };
        return GroupedOpenApi.builder().group("RentCarAPP Application").pathsToMatch(path).build();
    }
}
