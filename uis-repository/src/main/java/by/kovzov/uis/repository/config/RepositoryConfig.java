package by.kovzov.uis.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "by.kovzov.uis.repository.api")
@Configuration
public class RepositoryConfig {
}
