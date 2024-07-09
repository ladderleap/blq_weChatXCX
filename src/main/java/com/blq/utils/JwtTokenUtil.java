package com.blq.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtil {
        private static String secretKey = "www.blq.com";
        private static Long expire = 1000L*60*30;
        /**
         * 生成jwt
         * 使用Hs256算法, 私匙使用固定秘钥
         *
         * @param claims    设置的信息
         * @return
         */
        public static String createJWT(Map<String, Object> claims) {
            // 指定签名的时候使用的签名算法，也就是header那部分
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            // 生成JWT的时间
            long expMillis = System.currentTimeMillis() + expire;
            Date exp = new Date(expMillis);

            // 设置jwt的body
            JwtBuilder builder = Jwts.builder()
                    // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                    .setClaims(claims)
                    // 设置签名使用的签名算法和签名使用的秘钥
                    .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                    // 设置过期时间
                    .setExpiration(exp);

            return builder.compact();
        }


        public static Claims parseJWT(String token) {
            // 得到DefaultJwtParser
            Claims claims = Jwts.parser()
                    // 设置签名的秘钥
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    // 设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
            return claims;
        }
}
