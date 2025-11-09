package org.example.schedulingappdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchedulingAppDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingAppDevelopApplication.class, args);
    }

}
