package com.example.intelligenttransportation.security;

import com.example.intelligenttransportation.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 获取请求头中的Authorization
        String bearerToken = request.getHeader("Authorization");
        
        // 判断是否存在token且是否是Bearer类型
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // 截取token (去掉"Bearer ")
            String token = bearerToken.substring(7);
            
            // 验证token并解析用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);

            // 如果能够从token中解析出用户名且当前没有认证信息
            if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据用户名获取用户详情
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // 验证token是否有效
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    // 构建认证信息
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 设置认证信息到上下文中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        
        // 继续执行过滤器链
        chain.doFilter(request, response);
    }
} 