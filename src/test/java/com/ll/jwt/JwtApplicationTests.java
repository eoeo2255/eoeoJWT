package com.ll.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JwtApplicationTests {

    @Value("${custom/jwt/secretKey}")
    private String secretKey;

    @Test
    @DisplayName("secretKey 필수")
    void t1() {
        assertThat(secretKey).isNotNull();
    }

}
