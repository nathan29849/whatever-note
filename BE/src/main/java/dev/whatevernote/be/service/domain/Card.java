package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.common.BaseEntity;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE card SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class Card extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	protected Card() {}

	private Card(Long seq, String title, Note note) {
		this.seq = seq;
		this.title = title;
		this.note = note;
	}

	@Column(name="card_order")
	private Long seq;

	private String title;

	@ManyToOne
	@JoinColumn(name="note_id")
	private Note note;

	public static Card from(CardRequestDto cardRequestDto, Note note) {
		return new Card(cardRequestDto.getSeq(), cardRequestDto.getTitle(), note);
	}

	public Long getId() {
		return id;
	}

	public Long getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

	public Note getNote() {
		return note;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateSeq(Long seq) {
		this.seq = seq;
	}
}
