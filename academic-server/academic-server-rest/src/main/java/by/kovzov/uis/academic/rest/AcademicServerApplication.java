package by.kovzov.uis.academic.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.academic",
    "by.kovzov.uis.common.exception.handler",
    "by.kovzov.uis.common.validator",
})
public class AcademicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicServerApplication.class, args);
    }
}
