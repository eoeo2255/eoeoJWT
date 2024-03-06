package com.ll.jwt.provider;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;


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



}
