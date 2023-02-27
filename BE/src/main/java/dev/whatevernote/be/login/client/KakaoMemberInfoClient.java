package dev.whatevernote.be.login.client;

import dev.whatevernote.be.login.service.dto.response.KakaoAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-member-info-client", url = "${oauth.kakao.resource-url}")
public interface KakaoMemberInfoClient {

	@GetMapping
	KakaoAccountResponse call(
		@RequestHeader("Content-Type") String contentType,
		@RequestHeader("Authorization") String accessToken
	);

}
