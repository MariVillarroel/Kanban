package com.kanban.config;

import com.kanban.security.RateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;

@Configuration
public class SecurityConfig {
  @Bean
  public FilterRegistrationBean<RateLimitFilter> rateLimitRegistration(RateLimitFilter f){
    FilterRegistrationBean<RateLimitFilter> reg = new FilterRegistrationBean<>();
    reg.setFilter(f);
    reg.setOrder(1);
    return reg;
  }
}
