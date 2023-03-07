package dev.whatevernote.be.login;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class AuthExtractor {

	private static final String TOKEN_TYPE_BEARER = "Bearer";

	public static String extract(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaders(HttpHeaders.AUTHORIZATION);

		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
			if (isBearerType(value)) {
				return extractOAuthHeader(value);
			}
		}
		return null;
	}

	private static boolean isBearerType(String value) {
		return value.toLowerCase().startsWith(TOKEN_TYPE_BEARER.toLowerCase());
	}

	private static String extractOAuthHeader(String value) {
		return value.substring(TOKEN_TYPE_BEARER.length()).trim();
	}

}
