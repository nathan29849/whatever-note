package dev.whatevernote.be.login.service.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {

	private final Long memberId;
	private final String nickname;
	private final String profileImage;
	private final String email;
	private final String accessToken;
	private final String refreshToken;

	public LoginResponse(Long memberId, String nickname, String profileImage, String email, String accessToken, String refreshToken) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.email = email;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
