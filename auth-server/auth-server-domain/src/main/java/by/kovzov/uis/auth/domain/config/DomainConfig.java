package by.kovzov.uis.auth.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "by.kovzov.uis.auth.domain.entity")
public class DomainConfig {
}
