package com.lnr.authentication_service.auth.infrastructure.primary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class KeyRestResource {

    private final RSAPublicKey publicKey;



    @GetMapping("/jwks.json")
    public Map<String, String> getPublicKey() {
        // You can return in JWKS format or simple PEM string
        return Map.of(
                "alg", "RS256",
                "value", Base64.getEncoder().encodeToString(publicKey.getEncoded())
        );
    }
}
