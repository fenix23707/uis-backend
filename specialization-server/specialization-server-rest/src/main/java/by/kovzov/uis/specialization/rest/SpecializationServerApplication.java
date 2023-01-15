package by.kovzov.uis.specialization.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import by.kovzov.uis.auth.rest.security.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

//TODO:remove
@SpringBootApplication(scanBasePackages = {"by.kovzov.uis"})
@OpenAPIDefinition
@EnableConfigurationProperties(RsaKeyProperties.class) //TODO:remove
public class SpecializationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecializationServerApplication.class, args);
    }
}
