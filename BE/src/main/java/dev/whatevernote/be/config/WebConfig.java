package dev.whatevernote.be.config;

import dev.whatevernote.be.login.AuthInterceptor;
import dev.whatevernote.be.login.LoginArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;
	private final LoginArgumentResolver loginArgumentResolver;

	public WebConfig(AuthInterceptor authInterceptor, LoginArgumentResolver loginArgumentResolver) {
		this.authInterceptor = authInterceptor;
		this.loginArgumentResolver = loginArgumentResolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("*")
			.allowedOrigins("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(
				"/error/**",
				"/login/kakao/**",
				"/docs/**"
			);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginArgumentResolver);
	}
}
