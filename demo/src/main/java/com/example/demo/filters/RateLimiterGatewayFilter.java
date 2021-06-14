package com.example.demo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.configurations.RateLimiterGatewayFilterConfiguration;

import reactor.core.publisher.Mono;

@Component
public class RateLimiterGatewayFilter implements GatewayFilter {

	private static Logger logger = LoggerFactory.getLogger(RateLimiterGatewayFilter.class);

	@Autowired
	private RateLimiterGatewayFilterConfiguration configuration;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Rate limiter GATEWAY filter: {} request per {} seconds {}.",
				configuration.getLimitForPeriod(),
				configuration.getPeriodInSeconds(),
				configuration.isEnabled() ? "ENABLED" : "DISABLED");

		return chain.filter(exchange);
	}

}
