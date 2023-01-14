package by.kovzov.uis.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record RsaKeyProperties(
    RSAPrivateKey accessTokenPrivateKey,
    RSAPublicKey accessTokenPublicKey,
    RSAPrivateKey refreshTokenPrivateKey,
    RSAPublicKey refreshTokenPublicKey) {
}
