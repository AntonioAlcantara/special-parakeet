package com.parakeet.lol.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
public class LolConfig {

    @Value("${db.user}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPass;
    @Value("${db.name}")
    private String dbName;
    @Value("${db.host}")
    private String dbHost;
    @Value("${db.port}")
    private String dbPort;

    @Bean
    public DataSource getDataSource() {
        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(String.format(
                "jdbc:postgresql://%s:%s/%s",
                dbHost,
                dbPort,
                dbName
        ));
        dataSourceBuilder.username(dbUser);
        dataSourceBuilder.password(dbPass);
        return dataSourceBuilder.build();
    }
}
