package dev.whatevernote.be.login.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private final String secretKey;
	private final Long accessExpireTime;
	private final String accessTokenSubject;
	private final Long refreshExpireTime;
	private final String refreshTokenSubject;
	private final String issuer;
	private final String tokenType;

	public JwtProperties(String secretKey, Long accessExpireTime, String accessTokenSubject,
		Long refreshExpireTime, String refreshTokenSubject, String issuer, String tokenType) {
		this.secretKey = secretKey;
		this.accessExpireTime = accessExpireTime;
		this.accessTokenSubject = accessTokenSubject;
		this.refreshExpireTime = refreshExpireTime;
		this.refreshTokenSubject = refreshTokenSubject;
		this.issuer = issuer;
		this.tokenType = tokenType;
	}

}
