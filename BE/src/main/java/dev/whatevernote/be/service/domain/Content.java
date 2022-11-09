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
	private String content;

	private Boolean isImage = Boolean.FALSE;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	protected Content() {
	}

	private Content(String content, Boolean isImage, Card card) {
		this.content = content;
		this.isImage = isImage;
		this.card = card;
	}
}
