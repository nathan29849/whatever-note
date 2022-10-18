package dev.whatevernote.be.service.dto.request;

public class NoteRequestDto {

	private Long order;
	private String title;

	public NoteRequestDto(Long order, String title) {
		this.order = order;
		this.title = title;
	}

	public Long getOrder() {
		return order;
	}

	public String getTitle() {
		return title;
	}

}
