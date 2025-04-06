package com.example.intelligenttransportation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许特定来源而不是*
        config.addAllowedOrigin("http://localhost:8080");  // 前端开发服务器
        config.addAllowedOrigin("http://localhost:8081");  // 也可能是这个端口
        // 允许所有头部信息
        config.addAllowedHeader("*");
        // 允许所有HTTP方法（GET、POST等）
        config.addAllowedMethod("*");
        // 允许携带认证信息
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
} 