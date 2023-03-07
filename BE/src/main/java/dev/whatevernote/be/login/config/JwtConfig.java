package dev.whatevernote.be.login.config;

import dev.whatevernote.be.login.config.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

	private final JwtProperties jwtProperties;

	public JwtConfig(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}
}
