package com.cardavid.ms.gatewayserver.filter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilterImpl implements GlobalFilter {

	
	private static final Logger logger = LoggerFactory.getLogger(GlobalFilterImpl.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		logger.info(String.format("Ejecutando el filter antes del request..."));
		exchange.getAttributes().put("token_audit", "abcdef");

		Optional.ofNullable(exchange.getRequest().getPath()).ifPresent(request -> {
			logger.info(String.format("RequestApi: %s",request.value()));

			if (request.value().contains("item")){
				String requestHeader = exchange.getRequest().getHeaders().getFirst("X-Request");
				logger.info(String.format("requestHeader: "+requestHeader));
				
				Map<String, List<String>> map = exchange.getRequest().getQueryParams();
				logger.info(String.format("RequestParameter: "+map.get("X-Parameter")));
			}
		});
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> { 
			logger.info("Ejecutando el filter despues del request...");
	        String token = exchange.getAttribute("token_audit");
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
			exchange.getResponse().getHeaders().set("token", token);
		}));
	}
}
