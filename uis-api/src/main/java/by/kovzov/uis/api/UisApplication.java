package by.kovzov.uis.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import by.kovzov.uis.api.security.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication(scanBasePackages = {"by.kovzov.uis"})
@EnableConfigurationProperties(RsaKeyProperties.class)
@OpenAPIDefinition
public class UisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UisApplication.class, args);
    }
}
