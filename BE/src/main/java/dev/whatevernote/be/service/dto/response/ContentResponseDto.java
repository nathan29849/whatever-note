package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Content;

public class ContentResponseDto {

	private final Long id;
	private final Long seq;
	private final String info;
	private final Boolean isImage;

	public ContentResponseDto(Long id, Long seq, String info, Boolean isImage) {
		this.id = id;
		this.seq = seq;
		this.info = info;
		this.isImage = isImage;
	}

	public static ContentResponseDto from(Content content) {
		return new ContentResponseDto(
			content.getId(),
			content.getSeq(),
			content.getInfo(),
			content.getImage()
		);
	}
}
