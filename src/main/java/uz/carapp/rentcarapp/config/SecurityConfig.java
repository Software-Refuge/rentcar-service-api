package uz.carapp.rentcarapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.carapp.rentcarapp.security.AuthoritiesConstants;
import uz.carapp.rentcarapp.security.jwt.JWTFilter;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authz ->
                    authz
                            .requestMatchers("/ws/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers("/api/v1/account/auth").permitAll()
                            .requestMatchers("/api/v1/account/me").authenticated()
                            .requestMatchers("/api/v1/account/profile").authenticated()
                            .requestMatchers("/api/v1/account/change-password").authenticated()
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/actuator/prometheus").permitAll()
                            .requestMatchers("/api/v1/merchant/auth").permitAll()
                            .requestMatchers("/api/v1/merchant/me").authenticated()
                            .requestMatchers("/api/v1/merchant/select-branch").permitAll()
                            .requestMatchers("/metrics").permitAll()
                            //#--------------Merchant------------------------#
                            .requestMatchers("/api/v1/merchants/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/merchant-branches/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/merchants1/merchant/test").hasAuthority(AuthoritiesConstants.OWNER)
                            //#--------------Brand---------------------------#
                            .requestMatchers("/api/v1/brands").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Account-------------------------#
                            .requestMatchers("/api/v1/account/create-user").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/account/list").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Attachment----------------------#
                            .requestMatchers("/api/v1/attachments/save-file").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.OWNER)
                            //#--------------Color---------------------------#
                            .requestMatchers("/api/v1/colors/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Model---------------------------#
                            .requestMatchers("/api/v1/models/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/model-attachments/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/model-attachments/set-main-photo").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Param---------------------------#
                            .requestMatchers("/api/v1/params/**").hasAnyAuthority(AuthoritiesConstants.ADMIN,AuthoritiesConstants.OWNER)
                            //#--------------ParamValue----------------------#
                            .requestMatchers("/api/v1/param-values/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.OWNER)
                            //#--------------Translations--------------------#
                            .requestMatchers("/api/v1/translations/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Car-----------------------------#
                            .requestMatchers("/api/v1/merchant/cars/**").hasAuthority(AuthoritiesConstants.OWNER)
                            .requestMatchers("/api/v1/merchant/car-params/**").hasAuthority(AuthoritiesConstants.OWNER)
                            .requestMatchers("/api/v1/car-templates/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/car-template-params/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/merchant/car-attachments/**").hasAuthority(AuthoritiesConstants.OWNER)
                            .requestMatchers("/api/v1/merchant/car-attachments/set-main-photo").hasAuthority(AuthoritiesConstants.OWNER)
                            .requestMatchers("/api/v1/merchant/car-mileages").hasAuthority(AuthoritiesConstants.OWNER)
                            .anyRequest()
                            .authenticated())
            .exceptionHandling(
                    exceptions ->
                            exceptions
                                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
