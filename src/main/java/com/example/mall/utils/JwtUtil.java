package com.example.mall.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtil {

    // 过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 密钥
    public static final String APP_SECRET = "sakanal-ouyang-zhou";

    /**
     * 生成会员的jwtToken的方法
     *
     * @param id       用户id
     * @param userName 用户昵称
     * @return 根据id和userName生成的token
     */
    public static String getUserJwtToken(String id, String userName) {
        return Jwts.builder()
                //设置头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("mall-sakanal-ouyang-zhou")
                //设置过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置token主体部分
                .claim("id", id)
                .claim("userName", userName)
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 判断token是否存在与有效
     *
     * @param jwtToken 待判断的token
     * @return jwtToken是否存在与有效
     */
    public static boolean checkUserJwtToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     *
     * @param request 请求头
     * @return jwtToken是否存在与有效
     */
    public static boolean checkUserJwtToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据jwtToken获取会员id
     *
     * @param jwtToken 待解析的jwtToken
     * @return jwtToken中解析出的会员id
     */
    public static String getUserIdByJwtToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }

    /**
     * 根据token获取会员id
     *
     * @param request 请求头
     * @return jwtToken中解析出的会员id
     */
    public static String getUserIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}
