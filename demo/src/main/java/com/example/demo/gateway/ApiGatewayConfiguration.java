package com.example.demo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filters.RateLimiterGatewayFilter;

@Configuration
public class ApiGatewayConfiguration {

	@Autowired
	private RateLimiterGatewayFilter rateLimiterGatewayFilter;

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(p -> p.path("/get").filters(f -> f.filter(rateLimiterGatewayFilter)).uri("http://httpbin.org"))
				.build();
	}
}
