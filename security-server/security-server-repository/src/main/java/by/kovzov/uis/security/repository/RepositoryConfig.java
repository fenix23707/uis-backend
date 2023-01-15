package by.kovzov.uis.security.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import by.kovzov.uis.security.domain.SecurityDomainConfig;

@EnableJpaRepositories(basePackages = "by.kovzov.uis.auth.repository.api")
@Configuration
@Import(SecurityDomainConfig.class)
@PropertySource("classpath:application.yaml")
public class RepositoryConfig {
}
