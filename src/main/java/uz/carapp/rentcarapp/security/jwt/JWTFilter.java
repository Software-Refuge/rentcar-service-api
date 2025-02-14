package uz.carapp.rentcarapp.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import uz.carapp.rentcarapp.security.CustomUserDetails;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
@Component
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("JwtFiler Request - doFilter method!");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);

        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);

            if (authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

                // 🛑 `merchantId` va `branchId` bor yoki yo‘qligini tekshiramiz
                boolean isMerchantToken = customUserDetails.getMerchantId() != null && customUserDetails.getBranchId() != null;

                UserDetails userDetails;
                if (isMerchantToken) {
                    // 🔥 Merchant token bo‘lsa, `branchId` bilan yuklaymiz
                    //branchId va merchantId bo'yicha rollarni olish
                    userDetails = userDetailsService.loadUserByUsername(customUserDetails.getUsername(), customUserDetails.getUserId(), customUserDetails.getMerchantId(), customUserDetails.getBranchId());
                } else {
                    // 🔥 Oddiy token bo‘lsa, faqat login bilan yuklaymiz
                    userDetails = userDetailsService.loadUserByUsername(customUserDetails.getUsername());
                }
                System.out.println("userDetails: "+userDetails.getAuthorities());
                System.out.println("userNAme: "+userDetails.getUsername());


                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
