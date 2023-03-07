package dev.whatevernote.be.login.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties {

	private final String loginPageUrl;
	private final String responseType;
	private final String clientId;
	private final String redirectUri;
	private final String tokenType;
	private final String contentType;
	private final String grantType;

	public KakaoProperties(String loginPageUrl, String responseType, String clientId,
		String redirectUri, String tokenType, String contentType, String grantType) {
		this.loginPageUrl = loginPageUrl;
		this.responseType = responseType;
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.tokenType = tokenType;
		this.contentType = contentType;
		this.grantType = grantType;
	}
}
