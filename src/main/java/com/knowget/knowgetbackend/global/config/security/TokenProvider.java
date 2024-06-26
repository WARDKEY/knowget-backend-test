package com.knowget.knowgetbackend.global.config.security;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@PropertySource("classpath:jwt.yml")
@Service
public class TokenProvider {
	private final String secretKey;
	private final long expirationHours;
	private final String issuer;

	public TokenProvider(
		@Value("${secret-key}") String secretKey,
		@Value("${expiration-hours}") long expirationHours,
		@Value("${issuer}") String issuer
	) {
		this.secretKey = secretKey;
		this.expirationHours = expirationHours;
		this.issuer = issuer;
	}

	public String createToken(String userSpecification) {
		return Jwts.builder()
			.signWith(new SecretKeySpec(secretKey.getBytes(),
				SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
			.setSubject(userSpecification)  // JWT 토큰 제목
			.setIssuer(issuer)  // JWT 토큰 발급자
			.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
			.setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))    // JWT 토큰 만료 시간
			.compact(); // JWT 토큰 생성
	}

	public String validateTokenAndGetSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey.getBytes())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	// public void invalidateToken(String token) {
	// 	// 토큰 만료 시간을 현재 시간으로 설정하여 토큰을 무효화
	// 	Jwts.parserBuilder()
	// 		.setSigningKey(secretKey.getBytes())
	// 		.build()
	// 		.parseClaimsJws(token)
	// 		.getBody()
	// 		.setExpiration(Date.from(Instant.now()))
	// 		.compact();
	// }
}
