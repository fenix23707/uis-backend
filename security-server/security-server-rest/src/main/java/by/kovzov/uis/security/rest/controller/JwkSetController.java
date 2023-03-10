package by.kovzov.uis.security.rest.controller;

import java.util.Map;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security/")
@RequiredArgsConstructor
public class JwkSetController {

    private final @Qualifier("accessTokenJwkSet") JWKSet accessTokenJwkSet;

    @GetMapping("jwk-set-uri")
    public Map<String, Object> jwtSet() {
        return accessTokenJwkSet.toPublicJWKSet().toJSONObject();
    }
}
