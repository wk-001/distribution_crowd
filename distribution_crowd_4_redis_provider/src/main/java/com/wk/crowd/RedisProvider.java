package com.wk.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RedisProvider {

	public static void main(String[] args) {
		SpringApplication.run(RedisProvider.class, args);
	}

}
