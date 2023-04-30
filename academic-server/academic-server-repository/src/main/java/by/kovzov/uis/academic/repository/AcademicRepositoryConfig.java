package by.kovzov.uis.academic.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "by.kovzov.uis.academic.repository.api")
@EntityScan(basePackages = "by.kovzov.uis.academic.repository.entity")
public class AcademicRepositoryConfig {
}
