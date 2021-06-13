package com.santos.ratelimiter.algorithm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.santos.ratelimiter.RateLimiter;

class ConcurrentRateLimiterTest {

	private RateLimiter rateLimiterMock;
	private RateLimiter limiter;

	@BeforeEach
	void setup() {
		rateLimiterMock = mock(RateLimiter.class);
		limiter = new ConcurrentRateLimiter(rateLimiterMock);
	}
	
	@Test
	void allowRequest_whenAllowed() {
		Mockito.when(rateLimiterMock.allowRequest()).thenReturn(true);
		
		assertTrue(limiter.allowRequest());
		
		verify(rateLimiterMock, times(1)).allowRequest();
	}

	@Test
	void allowRequest_whenNotAllowed() {
		Mockito.when(rateLimiterMock.allowRequest()).thenReturn(false);
		
		assertFalse(limiter.allowRequest());
		
		verify(rateLimiterMock, times(1)).allowRequest();
	}

}
