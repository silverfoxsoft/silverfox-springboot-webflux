package com.silverfox.publicsvr.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;
import java.util.Optional;

/**
 * 功能描述：日志拦截器
 *
 * @DATE 2023/12/3
 * @AUTHOR Jing.Li
 */
@Component
@Slf4j
public class TraceIdFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();

        return chain.filter(exchange)
                .contextWrite(context -> {
                    var traceId = "";
                    if (headers.containsKey(ConstantsFields.TRACE_ID_KEY)) {
                        traceId = headers.get(ConstantsFields.TRACE_ID_KEY);
                        MDC.put(ConstantsFields.MDC_TRACE_ID_KEY, traceId);
                    } else if (!exchange.getRequest().getURI().getPath().contains("/actuator")) {
                        log.warn("TRACE_ID not present in header: {}", exchange.getRequest().getURI());
                    }
                    Context contextTmp = context.put(ConstantsFields.TRACE_ID_KEY, traceId);
                    exchange.getAttributes().put(ConstantsFields.TRACE_ID_KEY, traceId);
                    return contextTmp;
                });
    }

}


