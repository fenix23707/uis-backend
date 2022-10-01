package by.kovzov.uis.repository.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = "by.kovzov.uis.repository.mybatis.mapper")
@EnableTransactionManagement
public class MybatisConfig {
}
