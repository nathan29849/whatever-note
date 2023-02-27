package dev.whatevernote.be.login.service.provider;

import dev.whatevernote.be.login.client.KakaoTokenClient;
import dev.whatevernote.be.login.client.KakaoMemberInfoClient;
import dev.whatevernote.be.login.config.properties.KakaoProperties;
import dev.whatevernote.be.login.service.dto.response.KakaoAccessTokenResponse;
import dev.whatevernote.be.login.service.dto.response.KakaoAccountResponse;
import org.springframework.stereotype.Component;

@Component
public class KakaoProvider {

	private final KakaoTokenClient tokenClient;
	private final KakaoMemberInfoClient memberInfoClient;
	private final KakaoProperties properties;

	public KakaoProvider(KakaoTokenClient tokenClient, KakaoMemberInfoClient memberInfoClient,
		KakaoProperties properties) {
		this.tokenClient = tokenClient;
		this.memberInfoClient = memberInfoClient;
		this.properties = properties;
	}

	public KakaoAccountResponse getMemberInformation(String code) {
		KakaoAccessTokenResponse response = getAccessToken(code);
		String accessToken = response.getAccessToken();

		return memberInfoClient.call(properties.getContentType(),
			String.format("%s %s", properties.getTokenType(), accessToken));
	}

	private KakaoAccessTokenResponse getAccessToken(String authCode) {
		return tokenClient.call(
			properties.getContentType(),
			properties.getGrantType(),
			properties.getClientId(),
			properties.getRedirectUri(),
			authCode
		);
	}

}
