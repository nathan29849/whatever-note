package dev.whatevernote.be.login;

import dev.whatevernote.be.exception.bad_request.NotLoggedInMemberException;
import dev.whatevernote.be.login.service.provider.JwtProvider;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTH_TOKEN = "AUTH_TOKEN";
	private final JwtProvider jwtProvider;

	public LoginArgumentResolver(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Login.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String token = (String) Optional.ofNullable(request.getAttribute(AUTH_TOKEN))
			.orElseThrow(NotLoggedInMemberException::new);

		return Long.parseLong(jwtProvider.getAudience(token));
	}
}
