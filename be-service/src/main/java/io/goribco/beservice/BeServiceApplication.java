package io.goribco.beservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"io.goribco.beservice"})
@EnableScheduling
@EnableDiscoveryClient
public class BeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeServiceApplication.class, args);
    }

}
