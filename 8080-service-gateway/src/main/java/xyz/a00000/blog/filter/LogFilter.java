package xyz.a00000.blog.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***** before access *****");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        InetSocketAddress localAddress = request.getLocalAddress();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        log.info("access uri: " + uri.getPath());
        log.info("access localAddress: " + localAddress.getHostName());
        log.info("access remoteAddress: " + remoteAddress.getHostName());
        List<String> ip = request.getHeaders().getOrEmpty("ip");
        log.info("ip from header: " + Arrays.toString(ip.toArray()));
        Mono<Void> filter = chain.filter(exchange);
        log.info("***** after access *****");
        return filter;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
