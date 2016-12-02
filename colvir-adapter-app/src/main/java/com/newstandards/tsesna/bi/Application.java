package com.newstandards.tsesna.bi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@IntegrationComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplication(Application.class).run();
    }
}
