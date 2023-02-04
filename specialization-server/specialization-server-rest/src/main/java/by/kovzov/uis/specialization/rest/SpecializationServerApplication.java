package by.kovzov.uis.specialization.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.specialization",
    "by.kovzov.uis.common.exception.handler"
})
public class SpecializationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecializationServerApplication.class, args);
    }
}
