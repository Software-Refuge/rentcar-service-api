package uz.carapp.rentcarapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.domain.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String secretKey = "merchantSecret2025"; // .env yoki Config-da saqlash tavsiya qilinadi

    public String generateTemporaryToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600_000)) // 10 daqiqa
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateToken(User user, Long merchantId, Long branchId, List<MerchantRole> roles) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("merchantId", merchantId)
                .claim("branchId", branchId)
                .claim("roles", roles.stream().map(MerchantRole::getMerchantRoleType).collect(Collectors.toSet()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 24 soat
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
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
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
