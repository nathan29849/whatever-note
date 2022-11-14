package dev.whatevernote.be.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String info;

	private Long seq;
	private Boolean isImage = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	protected Content() {
	}

	public Long getId() {
		return id;
	}

	public String getInfo() {
		return info;
	}

	public Long getSeq() {
		return seq;
	}

	public Boolean getImage() {
		return isImage;
	}

	public Card getCard() {
		return card;
	}

	private Content(String info, Long seq, Boolean isImage, Card card) {
		this.info = info;
		this.seq = seq;
		this.isImage = isImage;
		this.card = card;
	}
}
