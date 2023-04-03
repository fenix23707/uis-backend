package by.kovzov.uis.security.rest;

import by.kovzov.uis.security.service.api.StartUpService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.security",
    "by.kovzov.uis.common.exception.handler",
    "by.kovzov.uis.common.validator"
})
@OpenAPIDefinition
@AllArgsConstructor
public class SecurityServerApplication implements CommandLineRunner {

    private final StartUpService startUpService;

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        startUpService.updateAdminUser();
    }
}
