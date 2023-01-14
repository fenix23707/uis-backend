import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import by.kovzov.uis.auth.rest.security.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication(scanBasePackages = {"by.kovzov.uis.auth"})
@EnableConfigurationProperties(RsaKeyProperties.class)
@OpenAPIDefinition
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
