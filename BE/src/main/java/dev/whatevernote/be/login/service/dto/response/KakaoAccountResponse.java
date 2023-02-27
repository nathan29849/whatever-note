package dev.whatevernote.be.login.service.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.whatevernote.be.login.config.KakaoAccountResponseDeserializer;
import lombok.Getter;

@Getter
@JsonDeserialize(using = KakaoAccountResponseDeserializer.class)
public class KakaoAccountResponse {

	private final String uniqueId;
	private final String nickname;
	private final String profileImage;
	private final String email;

	public KakaoAccountResponse(String uniqueId, String nickname, String profileImage, String email) {
		this.uniqueId = uniqueId;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.email = email;
	}
}
