package by.kovzov.uis.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;

import lombok.SneakyThrows;

@TestInstance(Lifecycle.PER_CLASS)
public class AbstractIntegrationRepositoryTest {

    private final String dataPath;

    @Autowired
    private DataSource dataSource;

    public AbstractIntegrationRepositoryTest(String dataPath) {
        this.dataPath = dataPath;
    }

    @BeforeAll
    @SneakyThrows
    void loadData() {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource(dataPath));
    }
}
