package by.kovzov.uis.specialization.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "by.kovzov.uis.specialization.repository.api")
@Configuration
@EntityScan(basePackages = "by.kovzov.uis.specialization.repository.entity")
@PropertySource("classpath:application.yaml")
public class SpecializationRepositoryConfig {
}
