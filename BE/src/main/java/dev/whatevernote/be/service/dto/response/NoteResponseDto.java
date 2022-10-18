package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Note;

public class NoteResponseDto {

	private Long id;
	private Long order;
	private String title;

	public NoteResponseDto(Long id, Long order, String title) {
		this.id = id;
		this.order = order;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public Long getOrder() {
		return order;
	}

	public String getTitle() {
		return title;
	}

	public static NoteResponseDto from(Note note) {
		return new NoteResponseDto(
			note.getId(),
			note.getOrder(),
			note.getTitle()
		);
	}

}
