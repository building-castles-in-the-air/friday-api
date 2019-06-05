package com.github.friday.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class JWTUtil {
	/**
	 * 校验token是否正确
	 * 
	 * @param token
	 *            密钥
	 * @param secret
	 *            用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 验证JWT是否被篡改, 如果被篡改会抛出异常
			algorithm.verify(JWT.decode(token));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * 
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	public static Claim getClaim(String token, String key) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(key);
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名, EXPIRE_TIME后过期
	 * 
	 * @param username
	 *            用户名
	 * @param secret
	 *            用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withClaim("username", username)
					.withClaim("createTime", System.currentTimeMillis())
					.sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static void verifyExpires(String token) {
		DecodedJWT jwt = JWT.decode(token);
		if (jwt.getExpiresAt().compareTo(new Date()) == -1)
			throw new RuntimeException("token expires");
	}

}
