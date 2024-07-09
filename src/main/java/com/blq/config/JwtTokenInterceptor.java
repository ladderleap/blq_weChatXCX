package com.blq.config;

import com.blq.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null) {
            Claims claims = JwtTokenUtil.parseJWT(token);
            if (claims != null) {
                return true; // Token is valid, allow the request
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        return false;
    }
}
