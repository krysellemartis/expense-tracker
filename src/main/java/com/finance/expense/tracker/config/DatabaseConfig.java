package com.finance.expense.tracker.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Log4j2
public class DatabaseConfig {

    @ConfigurationProperties("spring.datasource")
    public JdbcTemplate jdbcTemplate() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().build();
        return new JdbcTemplate(dataSource);
    }
}
