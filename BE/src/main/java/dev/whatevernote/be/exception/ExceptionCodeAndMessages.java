package dev.whatevernote.be.exception;

import dev.whatevernote.be.response.CodeAndMessages;

public enum ExceptionCodeAndMessages implements CodeAndMessages {
	;

	private final String code;
	private final String message;
	private final Class<? extends Exception> type;

	ExceptionCodeAndMessages(String code, String message, Class<? extends Exception> type) {
		this.code = code;
		this.message = message;
		this.type = type;
	}


	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
