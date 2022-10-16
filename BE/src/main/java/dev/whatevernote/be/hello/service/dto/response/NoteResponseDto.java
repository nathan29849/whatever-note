package dev.whatevernote.be.hello.service.dto.response;

public class NoteResponseDto {

	private Long id;
	private Integer order;
	private String title;

	public NoteResponseDto(Long id, Integer order, String title) {
		this.id = id;
		this.order = order;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public Integer getOrder() {
		return order;
	}

	public String getTitle() {
		return title;
	}
}
