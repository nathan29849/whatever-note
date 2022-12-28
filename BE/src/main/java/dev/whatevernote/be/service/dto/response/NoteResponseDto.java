package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Note;

public class NoteResponseDto {

	private final Integer id;
	private final Integer seq;
	private final String title;

	public NoteResponseDto(Integer id, Integer seq, String title) {
		this.id = id;
		this.seq = seq;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public Integer getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

	public static NoteResponseDto from(Note note) {
		return new NoteResponseDto(
			note.getId(),
			note.getSeq(),
			note.getTitle()
		);
	}

}
