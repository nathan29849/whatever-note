package dev.whatevernote.be.login.config;

import dev.whatevernote.be.login.config.properties.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
public class AuthConfig {

	private final KakaoProperties kakaoProperties;

	public AuthConfig(KakaoProperties kakaoProperties) {
		this.kakaoProperties = kakaoProperties;
	}
}
