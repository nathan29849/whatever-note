package dev.whatevernote.be.exception;

public class BaseException extends RuntimeException {

	private final String code;

	protected BaseException(ErrorCodeAndMessages errorCodeAndMessages) {
		super(errorCodeAndMessages.getMessage());
		this.code = errorCodeAndMessages.getCode();
	}

	public String getCode() {
		return code;
	}
}
