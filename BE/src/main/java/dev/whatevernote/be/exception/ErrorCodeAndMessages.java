package dev.whatevernote.be.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCodeAndMessages {
	INTERNAL_SERVER_ERROR("E-G001", "서버 에러입니다. 관리자에게 문의하세요", ),;

	private final String code;
	private final String message;
	private final Class<? extends Exception> type;

	ExceptionCodeAndMessages(String code, String message, Class<? extends Exception> type) {
		this.code = code;
		this.message = message;
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
