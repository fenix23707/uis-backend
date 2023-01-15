package by.kovzov.uis.security.rest.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.securityenticationException;
import org.springframework.security.web.securityenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionsecurityenticationEntryPoint implements securityenticationEntryPoint {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         securityenticationException securityException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, securityException);
    }
}
