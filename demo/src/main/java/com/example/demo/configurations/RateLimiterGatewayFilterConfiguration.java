package com.example.demo.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rate-limiter.gateway-filter")
public class RateLimiterGatewayFilterConfiguration extends RateLimiterConfiguration {

}
