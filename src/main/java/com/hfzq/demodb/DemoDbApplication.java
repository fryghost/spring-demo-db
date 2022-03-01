package com.hfzq.demodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDbApplication.class, args);
    }

}
