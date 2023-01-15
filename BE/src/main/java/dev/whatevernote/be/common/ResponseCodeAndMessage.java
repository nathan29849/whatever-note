package dev.whatevernote.be.common;

public enum ResponseCodeAndMessage {

	// NOTE
	NOTE_CREATE_SUCCESS("S-N001", "노트 생성을 성공했습니다."),
	NOTE_MODIFY_SUCCESS("S-N002", "노트 수정을 성공했습니다."),
	NOTE_REMOVE_SUCCESS("S-N003", "노트 삭제를 성공했습니다."),
	NOTE_RETRIEVE_DETAIL_SUCCESS("S-N004", "노트 개별 상세 조회를 성공했습니다."),
	NOTE_RETRIEVE_ALL_SUCCESS("S-N005", "노트 전체 조회를 성공했습니다."),

	// CARD
	CARD_CREATE_SUCCESS("S-CA001", "카드 생성을 성공했습니다."),
	CARD_MODIFY_SUCCESS("S-CA002", "카드 수정을 성공했습니다."),
	CARD_REMOVE_SUCCESS("S-CA003", "카드 삭제를 성공했습니다."),
	CARD_RETRIEVE_DETAIL_SUCCESS("S-CA004", "카드 개별 상세 조회를 성공했습니다."),
	CARD_RETRIEVE_ALL_SUCCESS("S-CA005", "카드 전체 조회를 성공했습니다."),

	// CONTENT
	CONTENT_CREATE_SUCCESS("S-C001", "컨텐트 생성을 성공했습니다."),
	CONTENT_MODIFY_SUCCESS("S-C002", "컨텐트 수정을 성공했습니다."),
	CONTENT_REMOVE_SUCCESS("S-C003", "컨텐트 삭제를 성공했습니다."),
	CONTENT_RETRIEVE_DETAIL_SUCCESS("S-C004", "컨텐트 개별 상세 조회를 성공했습니다."),
	CONTENT_RETRIEVE_ALL_SUCCESS("S-C005", "컨텐트 전체 조회를 성공했습니다.");

	private final String code;
	private final String message;

	ResponseCodeAndMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
