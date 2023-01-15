package dev.whatevernote.be.common;

public class BaseResponse<T> {

	private final String code;
	private final String message;
	private final T data;

	public BaseResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public BaseResponse(ResponseCodeAndMessages codeAndMessages, T data) {
		this.code = codeAndMessages.getCode();
		this.message = codeAndMessages.getMessage();
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
}
