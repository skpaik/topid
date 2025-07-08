package io.goribco.bgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"io.goribco.bgservice"})
@EnableScheduling
@EnableDiscoveryClient
public class BgServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BgServiceApplication.class, args);
    }

}
