package dev.whatevernote.be.login.service.provider;

import dev.whatevernote.be.exception.bad_request.InvalidJwtException;
import dev.whatevernote.be.login.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private final SecretKey secretKey;
	private final JwtParser jwtParser;

	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey()
			.getBytes(StandardCharsets.UTF_8));
		this.jwtParser = Jwts.parserBuilder()
			.requireIssuer(jwtProperties.getIssuer())
			.setSigningKey(secretKey)
			.build();
	}

	public String generateAccessToken(Long memberId) {
		return generateToken(
			memberId,
			jwtProperties.getAccessTokenSubject(),
			jwtProperties.getAccessExpireTime()
		);
	}

	public String generateRefreshToken(Long memberId) {
		return generateToken(
			memberId,
			jwtProperties.getRefreshTokenSubject(),
			jwtProperties.getRefreshExpireTime()
		);
	}

	private String generateToken(Long memberId, String accessTokenSubject, Long accessExpireTime) {
		long now = System.currentTimeMillis();
		Date expiration = new Date(now + accessExpireTime);

		return Jwts.builder()
			.setIssuer(jwtProperties.getIssuer())
			.setSubject(accessTokenSubject)
			.setAudience(String.valueOf(memberId))
			.setExpiration(expiration)
			.signWith(secretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = jwtParser.parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (JwtException | IllegalArgumentException exception) {
			throw new InvalidJwtException();
		}
	}

	public String getAudience(String token) {
		return jwtParser.parseClaimsJws(token).getBody().getAudience();
	}

}
