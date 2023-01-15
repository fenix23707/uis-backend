package by.kovzov.uis.security.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "by.kovzov.uis.security.domain.entity")
public class DomainConfig {
}
