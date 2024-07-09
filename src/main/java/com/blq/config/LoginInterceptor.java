package com.blq.config;

import com.alibaba.fastjson.JSONObject;
import com.blq.common.ErrorCode;
import com.blq.exception.BusinessException;
import com.blq.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: YL
 * @Desc:
 * @create: 2024-07-09 09:38
 **/

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    // 目标方法执行前调用  true：放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 1. 获得请求路径
        String url = request.getRequestURL().toString();

        // 2. 判断请求的资源路径
        if(url.contains("/wxxcxlogin")){
            // 登录功能，放行
            return true;
        }

        // 3. 获取请求头token
        String token = request.getHeader("Authorization");
        if(token == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 4. 解析token
        try{
            JwtTokenUtil.parseJWT(token);
        }catch (Exception e){
//            log.error("Failed to parse JWT token: {}", e.getMessage());
            System.out.println(e.getMessage());
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 5. token解析成功，放行
        return true;
    }

    // 目标方法执行后调用
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle...");
//    }
//
//    // 请求处理后调用
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("Completion...");
//    }
}
