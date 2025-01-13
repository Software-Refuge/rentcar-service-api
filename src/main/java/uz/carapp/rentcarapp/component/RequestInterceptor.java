package uz.carapp.rentcarapp.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;
import uz.carapp.rentcarapp.security.jwt.JwtProvider;


@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    private static final String SECRET_KEY = "rentcarr2024"; // JWT token uchun maxfiy kalit

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth != null ? auth.getName() : "anonymous";

        String requestURI = request.getRequestURI();   // So'rov URI
        String clientIP = request.getRemoteAddr();     // Foydalanuvchi IP manzili

        // MDC kontekstini to'ldirish
        MDC.put("userId", userId);
        MDC.put("requestURI", requestURI);
        MDC.put("clientIP", clientIP);

        logger.info("Incoming request: {} from {}", requestURI, clientIP);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // MDC kontekstni tozalash
        MDC.clear();
    }
}
