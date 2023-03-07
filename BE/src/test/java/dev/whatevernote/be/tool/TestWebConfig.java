package dev.whatevernote.be.tool;

import dev.whatevernote.be.config.WebConfig;
import dev.whatevernote.be.login.AuthInterceptor;
import dev.whatevernote.be.login.LoginArgumentResolver;
import dev.whatevernote.be.login.config.properties.JwtProperties;
import dev.whatevernote.be.login.service.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class TestWebConfig {

	@Autowired
	JwtProperties jwtProperties;

	@Bean
	WebConfig getWebConfig() {
		return new WebConfig(getAuthInterceptor(), getArgumentResolver());
	}

	@Bean
	@Primary
	AuthInterceptor getAuthInterceptor() {
		return new AuthInterceptor(getJwtProvider());
	}

	@Bean
	@Primary
	LoginArgumentResolver getArgumentResolver() {
		return new LoginArgumentResolver(getJwtProvider());
	}

	@Bean
	JwtProvider getJwtProvider() {
		return new JwtProvider(jwtProperties);
	}

}
