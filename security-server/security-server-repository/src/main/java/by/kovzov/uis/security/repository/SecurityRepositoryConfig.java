package by.kovzov.uis.security.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "by.kovzov.uis.security.repository.api")
@EnableJpaAuditing
@EntityScan(basePackages = "by.kovzov.uis.security.repository.entity")
@Configuration
@PropertySource("classpath:application.yaml")
public class SecurityRepositoryConfig {
}
