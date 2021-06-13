package com.santos.ratelimiter;

public interface RateLimiter {

	boolean allowRequest();

	long getLimitForPeriod();

	long getPeriodInSeconds();
}
