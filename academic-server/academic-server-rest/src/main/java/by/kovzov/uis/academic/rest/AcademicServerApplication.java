package by.kovzov.uis.academic.rest;

import by.vsu.uis.common.permission.PermissionHandlingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {
    "by.kovzov.uis.academic",
    "by.kovzov.uis.common.exception.handler",
    "by.kovzov.uis.common.validator",
})
@Import(value = {
    PermissionHandlingConfig.class
})
public class AcademicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicServerApplication.class, args);
    }
}
