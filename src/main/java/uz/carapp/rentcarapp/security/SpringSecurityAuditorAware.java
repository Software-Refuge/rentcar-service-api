package uz.carapp.rentcarapp.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import uz.carapp.rentcarapp.config.Constants;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM));
    }
}
