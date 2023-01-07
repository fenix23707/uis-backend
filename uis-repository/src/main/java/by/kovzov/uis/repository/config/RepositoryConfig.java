package by.kovzov.uis.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import by.kovzov.uis.domain.config.DomainConfig;

@EnableJpaRepositories(basePackages = "by.kovzov.uis.repository.api")
@Configuration
@Import(DomainConfig.class)
@PropertySource("classpath:application.yaml")
public class RepositoryConfig {
}
