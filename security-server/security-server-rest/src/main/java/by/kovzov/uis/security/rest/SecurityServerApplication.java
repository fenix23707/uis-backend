package by.kovzov.uis.security.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.security",
    "by.kovzov.uis.common.exception.handler"
})
@OpenAPIDefinition
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }
}
