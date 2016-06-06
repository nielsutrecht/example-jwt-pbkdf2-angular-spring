package com.nibado.example.jwtpbkdf2.security;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashUtilTest {
    private HashUtil hashUtil;

    @Before
    public void setup() {
        hashUtil = new HashUtil("PBKDF2WithHmacSHA1", 24, 24, 1000);
    }

    @Test
    public void testHash() {
        String hash1 = hashUtil.createHash("password1");
        String hash2 = hashUtil.createHash("password2");
        String hash3 = hashUtil.createHash("password1");

        assertThat(hash1).isNotEqualTo(hash2);
        assertThat(hash1).isNotEqualTo(hash3);

        assertThat(hashUtil.validatePassword("password1", hash1)).isTrue();
        assertThat(hashUtil.validatePassword("password2", hash2)).isTrue();
        assertThat(hashUtil.validatePassword("password1", hash3)).isTrue();

        assertThat(hashUtil.validatePassword("password2", hash1)).isFalse();
        assertThat(hashUtil.validatePassword("password1", hash2)).isFalse();

        assertThat(hash1.split(":").length).isEqualTo(3);
    }
}