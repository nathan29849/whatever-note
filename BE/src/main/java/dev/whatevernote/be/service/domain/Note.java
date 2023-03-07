package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.common.BaseEntity;
import dev.whatevernote.be.login.service.domain.Member;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	protected Note() {}

	private Note(Integer seq, String title, Member member) {
		this.seq = seq;
		this.title = title;
		this.member = member;
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

	public Member getMember() {
		return member;
	}

	public static Note from(NoteRequestDto noteRequestDto, Member member) {
		return new Note(noteRequestDto.getSeq(), noteRequestDto.getTitle(), member);
	}


	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateSeq(NoteRequestDto noteRequestDto) {
		this.seq = noteRequestDto.getSeq();
	}
}
