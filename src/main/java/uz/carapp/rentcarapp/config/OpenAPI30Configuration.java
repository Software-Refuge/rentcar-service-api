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
                "/api/v1/account/auth",
                "/api/v1/account/me",
                "/api/v1/account/profile",
                "/api/v1/account/change-password",
                "/api/v1/merchants/**",
                "/api/v1/merchant-branches/**",
                "/api/v1/brands/**",
                "/api/v1/account/create-user",
                "/api/v1/account/list",
                "/api/v1/attachments/save-file",
                "/api/v1/models/**",
                "/api/v1/colors/**",
                "/api/v1/params/**",
                "/api/v1/param-values/**",
                "/api/v1/translations/**",
                "/api/v1/merchants1/merchant/test/**",
                "/api/v1/car-templates/**",
                "/api/v1/car-template-params/**",
                "/api/v1/model-attachments/**",
                "/api/v1/model-attachments/set-main-photo",
                "/api/v1/documents",
                "/api/v1/doc-attachments",
                "/api/v1/merchant-documents"

        };
        return GroupedOpenApi.builder()
                .group("RentCarAPP Application").pathsToMatch(path).build();
    }

    @Bean
    public  GroupedOpenApi merchantOpenApi() {
        String[] merchantPath = {
                "/api/v1/merchant/auth",
                "/api/v1/merchant/select-branch",
                "/api/v1/merchant/me",
                "/api/v1/merchant/cars/**",
                "/api/v1/merchant/car-params/**",
                "/api/v1/merchant/car-attachments/**",
                "/api/v1/merchant/car-attachments/set-main-photo",
                "/api/v1/merchant/car-mileages"
        };

        return GroupedOpenApi.builder()
                .group("Merchant API").pathsToMatch(merchantPath).build();
    }
}
