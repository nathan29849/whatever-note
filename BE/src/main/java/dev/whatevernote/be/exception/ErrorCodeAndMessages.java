package dev.whatevernote.be.exception;

import dev.whatevernote.be.common.CodeAndMessages;

public enum ErrorCodeAndMessages implements CodeAndMessages {

	// 404 Not Found (존재하지 않는 리소스)
	E404_NOT_FOUND_NOTE("E-NF001", "노트의 아이디를 찾을 수 없습니다."),
	E404_NOT_FOUND_CARD("E-NF002", "카드의 아이디를 찾을 수 없습니다."),
	E404_NOT_FOUND_CONTENT("E-NF003", "컨텐트의 아이디를 찾을 수 없습니다."),
	E404_NOT_FOUND_MEMBER("E-NF004", "멤버 아이디를 찾을 수 없습니다."),

	// 400 Bad Request
	E400_INVALID_JWT_TOKEN("E-BR001", "유효하지 않은 JWT Token 입니다."),
	E400_NOT_LOGGED_IN_LOGIN_MEMBER("E-BR002", "로그인 되지 않은 멤버입니다."),
	;

	private final String code;
	private final String message;

	ErrorCodeAndMessages(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
