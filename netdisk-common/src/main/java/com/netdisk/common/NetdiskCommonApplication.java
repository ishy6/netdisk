package com.netdisk.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class NetdiskCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetdiskCommonApplication.class, args);
	}

}
