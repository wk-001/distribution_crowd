package com.wk.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableEurekaClient 专门针对Eureka注册中心
@EnableDiscoveryClient			//更为通用，注册中心不局限于eureka
@MapperScan("com.wk.mapper")	//配置扫描mybatis的mapper接口所在包
@SpringBootApplication
public class DataBaseProvider {

	public static void main(String[] args) {
		SpringApplication.run(DataBaseProvider.class,args);
	}

}
