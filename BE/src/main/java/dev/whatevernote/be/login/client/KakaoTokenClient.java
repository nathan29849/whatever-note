package dev.whatevernote.be.login.client;

import dev.whatevernote.be.login.service.dto.response.KakaoAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-token-client", url = "${oauth.kakao.token-url}")
public interface KakaoTokenClient {

	@PostMapping
	KakaoAccessTokenResponse call(
		@RequestHeader("Content-Type") String contentType,
		@RequestParam("grant_type") String grantType,
		@RequestParam("client_id") String clientId,
		@RequestParam("redirect_uri") String redirectUri,
		@RequestParam("code") String code
	);
}
