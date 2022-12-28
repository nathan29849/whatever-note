package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.common.BaseEntity;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE note SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class Note extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="note_order")
	@NotNull
	private Integer seq;

	private String title;

	protected Note() {}

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


	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateSeq(NoteRequestDto noteRequestDto) {
		this.seq = noteRequestDto.getSeq();
	}
}
