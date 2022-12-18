package dev.whatevernote.be.service.dto.request;

public class ContentRequestDto {

	private String info;
	private Long seq;
	private Boolean isImage;

	public ContentRequestDto(String info, Long seq, Boolean isImage) {
		this.info = info;
		this.seq = seq;
		this.isImage = isImage;
	}

	public String getInfo() {
		return info;
	}

	public Long getSeq() {
		return seq;
	}

	public Boolean getIsImage() {
		return isImage;
	}
}
