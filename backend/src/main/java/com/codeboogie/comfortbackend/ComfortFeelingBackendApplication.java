package com.codeboogie.comfortbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.codeboogie.comfortbackend")
@EnableJpaRepositories("com.codeboogie.comfortbackend")
@SpringBootApplication
public class ComfortFeelingBackendApplication {
    public static void main (String[] args) {
        SpringApplication.run(ComfortFeelingBackendApplication.class, args);
    }
}
