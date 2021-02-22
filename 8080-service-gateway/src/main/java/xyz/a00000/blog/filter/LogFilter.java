package xyz.a00000.blog.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.a00000.blog.bean.orm.AccessInfo;
import xyz.a00000.blog.mapper.AccessInfoMapper;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {

    @Autowired
    private AccessInfoMapper accessInfoMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("获取网关访问的地址和ip");
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.info("获得请求头中的ip.");
        List<String> ip = request.getHeaders().getOrEmpty("ip");
        log.info("构建访问信息对象.");
        AccessInfo info = new AccessInfo();
        info.setUrl(uri.getPath());
        if (ip.size() == 0) {
            info.setIp(String.format("{\"local\": \"%s\", \"remote\": \"%s\"}", request.getLocalAddress().getHostName(), request.getRemoteAddress().getHostName()));
        } else {
            info.setIp(Arrays.toString(ip.toArray()));
        }
        info.setAccessTime(new Date());
        log.info("持久化数据.");
        accessInfoMapper.insert(info);
        log.info("记录完成, 准备放行.");
        Mono<Void> filter = chain.filter(exchange);
        log.info("拦截完成.");
        return filter;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
