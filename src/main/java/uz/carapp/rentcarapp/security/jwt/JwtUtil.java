package uz.carapp.rentcarapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.domain.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final String AUTHORITIES_KEY = "auth"; // .env yoki Config-da saqlash tavsiya qilinadi

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateTemporaryToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600_000)) // 10 daqiqa
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateToken(User user, Long merchantId, Long branchId, List<MerchantRole> roles) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("userId", user.getId())
                .claim("merchantId", merchantId)
                .claim("branchId", branchId)
                .claim(AUTHORITIES_KEY, roles.stream().map(MerchantRole::getMerchantRoleType).collect(Collectors.toSet()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 24 soat
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String validateTemporaryToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
