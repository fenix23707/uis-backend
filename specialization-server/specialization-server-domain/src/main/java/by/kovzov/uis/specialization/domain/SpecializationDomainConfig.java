package by.kovzov.uis.specialization.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "by.kovzov.uis.specialization.domain.entity")
public class SpecializationDomainConfig {
}
