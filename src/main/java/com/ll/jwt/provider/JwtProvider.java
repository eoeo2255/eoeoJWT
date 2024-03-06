package com.ll.jwt.provider;

import com.ll.jwt.utill.Ut;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


@Component
public class JwtProvider {

    private SecretKey cachedSecretKey;

    @Value("${custom.jwt.secretKey}")
    private String secretKey;

    private SecretKey createSecretKey(){
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public SecretKey getSecretKey(){
        if (cachedSecretKey == null) {
            cachedSecretKey = createSecretKey();
        }
        return cachedSecretKey;
    }

    public String genToken(Map<String, Object> claims, int seconds){
        long now = new Date().getTime();
        Date tokenExpiration = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .claim("body", Ut.json.toStr(claims))
                .setExpiration(tokenExpiration)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean verify(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public Map<String ,Object> getClaims(String token){
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return Ut.json.toMap(body);
    }

}
