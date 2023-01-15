package dev.whatevernote.be.exception;

public enum ErrorCodeAndMessages {

	// 404 Not Found (존재하지 않는 리소스)
	E404_NOT_FOUND_NOTE("E-NF001", "노트의 아이디를 찾을 수 없습니다."),
	E404_NOT_FOUND_CARD("E-NF002", "카드의 아이디를 찾을 수 없습니다."),
	E404_NOT_FOUND_CONTENT("E-NF003", "컨텐트의 아이디를 찾을 수 없습니다.");

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
