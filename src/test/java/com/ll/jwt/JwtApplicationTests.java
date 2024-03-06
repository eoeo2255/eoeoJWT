package com.ll.jwt;

import com.ll.jwt.provider.JwtProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JwtApplicationTests {

    @Value("${custom.jwt.secretKey}")
    private String secretKey;

    @Autowired
    private JwtProvider provider;

    @Test
    @DisplayName("secretKey 필수")
    void t1() {
        assertThat(secretKey).isNotNull();
    }

    @Test
    @DisplayName("secretKey 생성")
    void t2() {
        //  key encoding
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        //  encoding 된 key로 SecretKey 객체 생성
        SecretKey secretKey1 = Keys.hmacShaKeyFor(encodedKey.getBytes());

        assertThat(secretKey1).isNotNull();
    }

    @Test
    @DisplayName("secretKey provider")
    void t3() {
        SecretKey secretKey = provider.getSecretKey();

        assertThat(secretKey).isNotNull();
    }

    @Test
    @DisplayName("secretKey는 한 번만 생성")
    void t4() {
        SecretKey secretKey1 = provider.getSecretKey();
        SecretKey secretKey2 = provider.getSecretKey();

        assertThat(secretKey1 == secretKey2).isTrue();
    }

    @Test
    @DisplayName("payload")
    void t5() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("name", "admin");

        String accessToken = provider.genToken(claims, 60*60*5);
        System.out.println("accessToken : "+accessToken);
        assertThat(accessToken).isNotNull();
    }

}
