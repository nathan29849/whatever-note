package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="note_order")
	private Long order;
	private String title;

	public Note() {}

	private Note(Long order, String title) {
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

	public static Note from(NoteRequestDto noteRequestDto) {
		return new Note(noteRequestDto.getOrder(), noteRequestDto.getTitle());
	}


}
