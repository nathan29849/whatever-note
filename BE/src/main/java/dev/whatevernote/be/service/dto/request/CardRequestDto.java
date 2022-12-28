package dev.whatevernote.be.service.dto.request;

public class CardRequestDto {

	private Long seq;
	private String title;

	private CardRequestDto() {
	}

	public CardRequestDto(Long seq, String title) {
		this.seq = seq;
		this.title = title;
	}

	public Long getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}
}
