package com.kocaeli.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration
{
    @Bean
    public DataSource dataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:kou.db");
        return dataSourceBuilder.build();
    }
}