package com.parakeet.lol.datasource;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

@Log4j2
public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {
    private static final String IMAGE_VERSION = "postgres:13-alpine";
    private static final String DB_USER = "taric";
    private static final String DB_PASS = "test";
    private static final String DB_NAME = "lol";
    private static PostgresTestContainer container;

    private PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer();
        }
        container.withTmpFs(Map.of("/var/lib/postgresql/data", "rw"));
        return container.withInitScript("dbscript/initdb-test.sql")
                .withEnv(Map.of(
                        "POSTGRES_PASSWORD", DB_PASS,
                        "POSTGRES_DB", DB_NAME,
                        "POSTGRES_USER", DB_USER
                )).withDatabaseName(DB_NAME)
                .withPassword(DB_PASS)
                .withUsername(DB_USER)
                .withExposedPorts(5432, 5432);

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            PostgresTestContainer container = PostgresTestContainer.getInstance();
            container.start();
            System.setProperty("db.host", container.getHost());
            System.setProperty("db.port", String.valueOf(container.getFirstMappedPort()));
            System.setProperty("db.user", container.getUsername());
            System.setProperty("db.password", container.getPassword());
            System.setProperty("db.name", container.getDatabaseName());
        }
    }
}
