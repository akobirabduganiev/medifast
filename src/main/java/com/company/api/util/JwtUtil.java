package com.company.api.util;


import com.company.api.dto.JwtDTO;
import com.company.api.error.AppForbiddenException;
import io.jsonwebtoken.*;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class JwtUtil {
    private final static String SECRET_KEY = "aniq topolmaysan";

    public static String createJwt(Long id, String username) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 100000L * 100 * 100 * 24));
        jwtBuilder.claim("userName", username);
        jwtBuilder.setIssuer("YUNEK");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeJwt(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(SECRET_KEY);
            Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = jws.getBody();
            Long id = Long.valueOf(claims.getSubject());
            String userName = (String) claims.get("userName");
            return new JwtDTO(id, userName);
        } catch (JwtException e) {
            throw new AppForbiddenException("JWT incorrect or time expired");
        }
    }
}