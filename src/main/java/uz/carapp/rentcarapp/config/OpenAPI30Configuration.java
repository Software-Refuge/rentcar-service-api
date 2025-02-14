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
                "/api/v1/models/**",
                "/api/v1/colors/**",
                "/api/v1/params/**",
                "/api/v1/param-values/**",
                "/api/v1/translations/**",
                "/api/v1/merchants1/merchant/test/**"

        };
        return GroupedOpenApi.builder()
                .group("RentCarAPP Application").pathsToMatch(path).build();
    }

    @Bean
    public  GroupedOpenApi merchantOpenApi() {
        String[] merchantPath = {
                "/api/merchant/auth",
                "/api/merchant/select-branch",
                "/api/merchant/me",
                "/api/merchant/cars",
                "/api/merchant/car-params"
        };

        return GroupedOpenApi.builder()
                .group("Merchant API").pathsToMatch(merchantPath).build();
    }
}
