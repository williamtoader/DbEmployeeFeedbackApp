package com.db.cloudschool.employeefeedback.security.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class SecretsService {
    // Generate key for HMAC SHA 256
    private final Key accessTokenSignatureKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Key getAccessTokenSignatureKey() {
        return accessTokenSignatureKey;
    }

}
