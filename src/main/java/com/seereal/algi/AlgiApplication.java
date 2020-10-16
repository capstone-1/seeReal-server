package com.seereal.algi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AlgiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgiApplication.class, args);
    }

}
