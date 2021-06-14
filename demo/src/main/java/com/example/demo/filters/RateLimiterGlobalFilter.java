package com.example.demo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.configurations.RateLimiterGlobalFilterConfiguration;

import reactor.core.publisher.Mono;

@Component
public class RateLimiterGlobalFilter implements GlobalFilter {

	private static Logger logger = LoggerFactory.getLogger(RateLimiterGlobalFilter.class);

	@Autowired
	private RateLimiterGlobalFilterConfiguration configuration;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Rate-limiter global filter: {} request per {} seconds is {}.",
				configuration.getLimitForPeriod(),
				configuration.getPeriodInSeconds(),
				configuration.isEnabled() ? "ENABLED" : "DISABLED");

		if (!configuration.isEnabled())
			return chain.filter(exchange);

		return chain.filter(exchange);
	}

}
