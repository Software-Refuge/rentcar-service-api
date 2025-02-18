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
                            .requestMatchers("/api/account/auth").permitAll()
                            .requestMatchers("/api/account/me").authenticated()
                            .requestMatchers("/api/account/profile").authenticated()
                            .requestMatchers("/api/account/change-password").authenticated()
                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/actuator/prometheus").permitAll()
                            .requestMatchers("/api/merchant/auth").permitAll()
                            .requestMatchers("/api/merchant/me").authenticated()
                            .requestMatchers("/api/merchant/select-branch").permitAll()
                            .requestMatchers("/metrics").permitAll()
                            //#--------------Merchant------------------------#
                            .requestMatchers("/api/v1/merchants/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/merchant-branches/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/v1/merchants1/merchant/test").hasAuthority(AuthoritiesConstants.OWNER)
                            //#--------------Brand---------------------------#
                            .requestMatchers("/api/v1/brands").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Account-------------------------#
                            .requestMatchers("/api/account/create-user").hasAuthority(AuthoritiesConstants.ADMIN)
                            .requestMatchers("/api/account/list").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Attachment----------------------#
                            .requestMatchers("/api/attachments/save-file").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Color---------------------------#
                            .requestMatchers("/api/v1/colors/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Model---------------------------#
                            .requestMatchers("/api/v1/models/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Param---------------------------#
                            .requestMatchers("/api/v1/params/**").hasAnyAuthority(AuthoritiesConstants.ADMIN,AuthoritiesConstants.OWNER)
                            //#--------------ParamValue----------------------#
                            .requestMatchers("/api/v1/param-values/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.OWNER)
                            //#--------------Translations--------------------#
                            .requestMatchers("/api/v1/translations/**").hasAuthority(AuthoritiesConstants.ADMIN)
                            //#--------------Car-----------------------------#
                            .requestMatchers("/api/merchant/cars/**").hasAuthority(AuthoritiesConstants.OWNER)
                            .requestMatchers("/api/merchant/car-params/**").hasAuthority(AuthoritiesConstants.OWNER)
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
