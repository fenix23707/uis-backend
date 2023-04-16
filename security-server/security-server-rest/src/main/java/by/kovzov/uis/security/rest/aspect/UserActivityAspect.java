package by.kovzov.uis.security.rest.aspect;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserActivityAspect {

    private final UserService userService;

    @AfterReturning(pointcut = "execution(* by.kovzov.uis.security.service.impl.TokenServiceImpl.createTokens(..))",
        returning = "jwtAuthenticationDto")
    public void updateLastActivity(JwtAuthenticationDto jwtAuthenticationDto) {
        userService.updateUserLastActivity(jwtAuthenticationDto.getId());
    }
}
