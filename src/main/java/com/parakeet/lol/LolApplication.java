package com.parakeet.lol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LolApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolApplication.class, args);
    }

}
