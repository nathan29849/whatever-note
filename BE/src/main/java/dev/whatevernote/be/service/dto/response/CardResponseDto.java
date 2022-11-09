package dev.whatevernote.be.service.dto.response;

public class CardResponseDto {

	private final Long id;
	private final String title;
	private final Long seq;

	public CardResponseDto(Long id, String title, Long seq) {
		this.id = id;
		this.title = title;
		this.seq = seq;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Long getSeq() {
		return seq;
	}
}
