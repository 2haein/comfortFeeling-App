package com.codeboogie.comfortbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EntityScan("com.codeboogie.comfortbackend")
@EnableMongoRepositories("com.codeboogie.comfortbackend")
@SpringBootApplication
public class ComfortFeelingBackendApplication {
    public static void main (String[] args) {
        SpringApplication.run(ComfortFeelingBackendApplication.class, args);
    }
}
