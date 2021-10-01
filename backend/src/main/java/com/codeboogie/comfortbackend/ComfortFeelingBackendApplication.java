package com.codeboogie.comfortbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComfortFeelingBackendApplication {
    public static void main (String[] args) {
        SpringApplication.run(ComfortFeelingBackendApplication.class, args);
    }
}
