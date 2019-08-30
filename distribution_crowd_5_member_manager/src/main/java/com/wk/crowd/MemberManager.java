package com.wk.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MemberManager {

	public static void main(String[] args) {
		SpringApplication.run(MemberManager.class,args);
	}

}
