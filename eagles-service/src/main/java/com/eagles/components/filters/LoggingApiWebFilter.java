package com.eagles.components.filters;

import com.eagles.components.filters.utilities.BodyCaptureExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingApiWebFilter implements WebFilter {

    private static final String API_URI_PATH = "/api/";

    @Override
    public Mono<Void> filter(final ServerWebExchange serverWebExchange, final WebFilterChain webFilterChain) {
        BodyCaptureExchange bodyCaptureExchange = new BodyCaptureExchange(serverWebExchange);
        return webFilterChain.filter(bodyCaptureExchange).doFinally(se -> {
            if (serverWebExchange.getRequest().getPath().toString().startsWith(API_URI_PATH)) {
                logRequest(serverWebExchange.getRequest(), bodyCaptureExchange.getRequestBody());
                logResponse(serverWebExchange.getRequest(), serverWebExchange.getResponse(), bodyCaptureExchange.getResponseBody());
            }
        });
    }

    private void logRequest(final ServerHttpRequest request, final String requestBody) {
        log.info("Processing request {} {} with params: {} , headers: {} and body: {}",
                request.getMethod(),
                request.getPath(),
                request.getQueryParams(),
                request.getHeaders(),
                requestBody
        );
    }

    private void logResponse(final ServerHttpRequest request, final ServerHttpResponse response, final String responseBody) {
        log.info("Processing response {} {} with status: {} , headers {} and body: {}",
                request.getMethod(),
                request.getPath(),
                response.getStatusCode(),
                response.getHeaders(),
                responseBody
        );
    }
}
