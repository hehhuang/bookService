package com.book.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String VALID_TOKEN = "aaa";
    private static final String TOKEN_HEADER = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求头中的 Token
        String token = request.getHeader(TOKEN_HEADER);

        // 校验 Token
        if (VALID_TOKEN.equals(token)) {
            return true;  // 验证通过，继续执行请求
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 未授权
            response.getWriter().write("Invalid Token");
            return false;  // 验证失败，终止请求
        }
    }
}