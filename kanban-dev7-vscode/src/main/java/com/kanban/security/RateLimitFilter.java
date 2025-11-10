package com.kanban.security;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.*;

@Component
public class RateLimitFilter implements Filter {
  private final ConcurrentMap<String,Bucket> buckets = new ConcurrentHashMap<>();

  private Bucket resolve(String key){
    return buckets.computeIfAbsent(key, k -> Bucket.builder()
        .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
        .build());
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    HttpServletResponse w = (HttpServletResponse) res;
    String ip = r.getRemoteAddr();
    Bucket b = resolve(ip);
    if (b.tryConsume(1)) {
      chain.doFilter(req, res);
    } else {
      w.setStatus(429);
      w.setContentType("application/json");
      w.getWriter().write("{"error":{"code":"RATE_LIMIT","message":"Demasiadas peticiones"}}");
    }
  }
}
