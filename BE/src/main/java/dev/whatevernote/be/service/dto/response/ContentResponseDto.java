package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Content;

public class ContentResponseDto {

	private Long id;
	private Long seq;
	private String info;
	private Boolean isImage;
	private Long cardId;

	protected ContentResponseDto() {
	}

	public ContentResponseDto(Long id, Long seq, String info, Boolean isImage, Long cardId) {
		this.id = id;
		this.seq = seq;
		this.info = info;
		this.isImage = isImage;
		this.cardId = cardId;
	}

	public static ContentResponseDto from(Content content) {
		return new ContentResponseDto(
			content.getId(),
			content.getSeq(),
			content.getInfo(),
			content.getIsImage(),
			content.getCard().getId()
		);
	}

	public Long getId() {
		return id;
	}

	public Long getSeq() {
		return seq;
	}

	public String getInfo() {
		return info;
	}

	public Boolean getIsImage() {
		return isImage;
	}

	public Long getCardId() {
		return cardId;
	}
}
