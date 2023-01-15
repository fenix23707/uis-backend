package by.kovzov.uis.security.rest.security;

import org.springframework.security.core.securityentication;

import by.kovzov.uis.security.domain.dto.JwtsecurityenticationDto;

public interface TokenService {


    JwtsecurityenticationDto createToken(securityentication securityentication);
}
