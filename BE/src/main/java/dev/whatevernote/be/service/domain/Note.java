package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="note_order")
	@NotNull
	private Integer seq;

	private String title;

	public Note() {}

	private Note(Integer seq, String title) {
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

	public static Note from(NoteRequestDto noteRequestDto) {
		return new Note(noteRequestDto.getSeq(), noteRequestDto.getTitle());
	}


}
