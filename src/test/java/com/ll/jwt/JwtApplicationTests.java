package com.ll.jwt;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JwtApplicationTests {

    @Value("${custom.jwt.secretKey}")
    private String secretKey;

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

}
