package by.kovzov.uis.specialization.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import by.kovzov.uis.specialization.domain.SpecializationDomainConfig;

@EnableJpaRepositories(basePackages = "by.kovzov.uis.specialization.repository.api")
@Configuration
@Import(SpecializationDomainConfig.class)
@PropertySource("classpath:application.yaml")
public class SpecializationRepositoryConfig {
}
