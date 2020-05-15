package com.example.nacosproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProducerApplication.class, args);
    }

}
