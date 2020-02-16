package com.netdisk.clientA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker // 开启容错服务 hystrix注解

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients // 开启Feign支持注解
public class NetdiskClientAApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetdiskClientAApplication.class, args);
	}

	@LoadBalanced // 启用Ribbon负载均衡
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	} // Spring 提供的访问其他服务的方法
}
