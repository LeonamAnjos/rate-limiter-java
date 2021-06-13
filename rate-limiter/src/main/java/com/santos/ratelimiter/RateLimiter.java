package com.santos.ratelimiter;

public interface RateLimiter {

	boolean allowRequest();
}

//replenishRate: The redis-rate-limiter.replenishRate is how many requests per second do you want a user to be allowed to do, without any dropped requests. This is the rate that the token bucket is filled.
//burstCapacity: The redis-rate-limiter.burstCapacity is the maximum number of requests a user is allowed to do in a single second. This is the number of tokens the token bucket can hold. Setting this value to zero will block all requests.


//limitForPeriod:
//limitRefreshPeriod:
//timeoutDuration:

// 100 requests por 3600 seconds
// 0.0278 * (4) = 1

// TimeUnit.HOURS.toMillis(1); 
//TimeUnit.HOURS.toSeconds(1); 


/*

maxBucket  = 120
period     =   1 min

refillRate =   2 / sec

current bucket = 100;
elapsed time   = 4 sec

4 * 2 / 1 = 8

elasedTme * (maxBucket / period) / 1 second


4 * (120 / 1) / 60 = 480 / 60 = 8







*/