package by.kovzov.uis.security.rest;

import by.kovzov.uis.security.service.api.StartUpService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SecurityServerApplication implements CommandLineRunner {

    private final StartUpService startUpService;

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            startUpService.updateAdminUser();
        } catch (Exception e) {
            log.warn("Unable to update admin user: " + e.getMessage());
        }
    }
}
