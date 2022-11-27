package by.kovzov.uis.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "by.kovzov.uis.domain.entity")
public class DomainConfig {
}
