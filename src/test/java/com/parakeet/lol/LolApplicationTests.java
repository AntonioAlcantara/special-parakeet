package com.parakeet.lol;

import com.parakeet.lol.datasource.PostgresTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class LolApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void postgresContainerIsRunning() {
        Assertions.assertTrue(PostgresTestContainer.getInstance().isRunning());
    }
}
