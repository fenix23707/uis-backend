package by.kovzov.uis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "by.kovzov.uis")
public class UisApplication {

    public static void main(String[] args) {
        SpringApplication.run(UisApplication.class, args);
    }
}
