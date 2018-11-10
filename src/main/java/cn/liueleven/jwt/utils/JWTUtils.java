package cn.liueleven.jwt.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @description: jwt 登录验证
 * @date: 2018-11-09 00:21
 * @author: 十一
 */
public class JWTUtils {

    // 秘钥
    private static final String SECRET = "custom_secret";
    // 签发人
    private static final String ISSUER = "web_user";

    /**
     * 生成token
     * @param claims 那些参数参与签名加密
     * @return
     */
    public static String genToken(Map<String,String> claims) {
        try {
            // 使用hmac256算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 签发人+过期时间+用户信息=token
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(org.apache.commons.lang3.time.DateUtils.addDays(new Date(), 1));
            claims.forEach((k,v) -> builder.withClaim(k,v));
            return builder.sign(algorithm).toString();
        } catch (UnsupportedEncodingException e) {
            // todo
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static Map<String,String> verifyToken(String token) {
        Algorithm algorithm = null;
        Map<String,String> resultMap = Maps.newHashMap();
        try {
            // token的逆运算
            algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier build = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT verify = build.verify(token);

            // 获得用户信息
            Map<String, Claim> claims = verify.getClaims();
            claims.forEach((k,v)->resultMap.put(k,v.asString()));
        }catch (UnsupportedEncodingException e) {
            // todo
            e.printStackTrace();
        }
        return resultMap;
    }
}
