package com.example.demo.filters;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

import com.santos.ratelimiter.RateLimiter;
import com.santos.ratelimiter.RateLimiterRegistry;

import reactor.core.publisher.Mono;

public abstract class RateLimiterFilter {
	private static Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

	private RateLimiterRegistry rateLimiterRegistry;

	protected abstract long getPeriodInSeconds();

	protected abstract long getLimitForPeriod();

	protected RateLimiterRegistry getRateLimiterRegistry() {
		if (rateLimiterRegistry == null) {
			logger.info("Rate Limiter Registry of: {} requests per {} seconds", getLimitForPeriod(), getPeriodInSeconds());
			rateLimiterRegistry = RateLimiterRegistry.of(getLimitForPeriod(), getPeriodInSeconds());
		}

		return rateLimiterRegistry;
	}

	protected RateLimiter getRateLimiter(String requester) {
		return getRateLimiterRegistry().rateLimiter(requester);
	}

	protected Mono<Void> errorResponse(ServerHttpResponse response) {
		response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);

		String message = String.format("Rate limit exceeded. Try again in %d seconds.", getWaitTime().longValue() );
		DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(buffer));
	}

	private BigDecimal getWaitTime() {
		BigDecimal factor  = valueOf(getLimitForPeriod()).divide(valueOf(getPeriodInSeconds()), 10, RoundingMode.HALF_UP);
		return valueOf(1).divide(factor, 10, RoundingMode.HALF_UP).add(valueOf(1));
	}
}
