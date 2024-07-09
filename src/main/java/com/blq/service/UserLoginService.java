package com.blq.service;

import com.blq.common.ErrorCode;
import com.blq.exception.BusinessException;
import com.blq.model.domain.LoginUser;
import com.blq.model.request.LoginResponse;
import com.blq.model.request.UserLoginRequest;
import com.blq.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLoginService {
    public LoginResponse userLogin(UserLoginRequest userLoginRequest) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appid = "wx95df7830e83d0521";
        String secret = "59e5fcb5db1112f18cdc03c19c2197fe";
        String jsCode = userLoginRequest.getCode();  // Replace with actual js_code
        String grantType = "authorization_code";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", appid));
        params.add(new BasicNameValuePair("secret", secret));
        params.add(new BasicNameValuePair("js_code", jsCode));
        params.add(new BasicNameValuePair("grant_type", grantType));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseString = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();
                // 将响应字符串转换为 Java 对象
                LoginUser loginUser = objectMapper.readValue(responseString, LoginUser.class);
                System.out.println(loginUser);


                Map<String, Object> claims = new HashMap<>();
                claims.put("openid", loginUser.getOpenid());
//                claims.put("unionid", loginUser.getUnionid());
                String token = JwtTokenUtil.createJWT(claims);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setToken(token);
                return loginResponse;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取用户信息失败");
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
