package com.example.demo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.configurations.RateLimiterGlobalFilterConfiguration;
import com.example.demo.utilities.ResquesterIdentifier;
import com.santos.ratelimiter.RateLimiter;
import com.santos.ratelimiter.RateLimiterRegistry;

import reactor.core.publisher.Mono;

@Component
public class RateLimiterGlobalFilter implements GlobalFilter {

	private static Logger logger = LoggerFactory.getLogger(RateLimiterGlobalFilter.class);

	@Autowired
	private RateLimiterGlobalFilterConfiguration configuration;

	@Autowired
	private ResquesterIdentifier requesterIdentifier;

	@Autowired
	private RateLimiterRegistry rateLimiterRegistry;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Rate-limiter global filter: {} request per {} seconds is {}.",
				configuration.getLimitForPeriod(),
				configuration.getPeriodInSeconds(),
				configuration.isEnabled() ? "ENABLED" : "DISABLED");

		if (!configuration.isEnabled())
			return chain.filter(exchange);

		String requester = requesterIdentifier.identify(exchange.getRequest());
		logger.info(requester);

		RateLimiter rateLimiter = rateLimiterRegistry.reateLimiter(requester);
		logger.info("IsAllowed? {}", rateLimiter.allowRequest());

		return chain.filter(exchange);
	}

	@Bean
	private RateLimiterRegistry rateLimiterRegistry() {
		logger.info("Rate Limiter Registry of: {} requests per {} seconds", configuration.getLimitForPeriod(), configuration.getPeriodInSeconds());
		return RateLimiterRegistry.of(configuration.getLimitForPeriod(), configuration.getPeriodInSeconds());
	}
}
