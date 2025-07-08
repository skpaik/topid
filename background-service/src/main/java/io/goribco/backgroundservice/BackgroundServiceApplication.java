package io.goribco.backgroundservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"io.goribco.backgroundservice"})
@EnableScheduling
@EnableDiscoveryClient
public class BackgroundServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgroundServiceApplication.class, args);
    }

}
