package com.example.demo.utilities;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class ResquesterIdentifier {

	public String identify(ServerHttpRequest request) {
		// TODO: implement a proper requester identifier
		return request.getRemoteAddress().getHostString();
	}
}
