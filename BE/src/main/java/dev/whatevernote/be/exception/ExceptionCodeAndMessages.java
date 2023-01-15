package dev.whatevernote.be.exception;

public enum ExceptionCodeAndMessages {
	;

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
