package dev.whatevernote.be.service.domain;

import dev.whatevernote.be.common.BaseEntity;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE content SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
public class Content extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String info;

	@Column(name="content_order")
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

	public Boolean getIsImage() {
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

	public static Content from(ContentRequestDto contentRequest, Card card) {
		return new Content(
			contentRequest.getInfo(),
			contentRequest.getSeq(),
			contentRequest.getIsImage(),
			card
		);
	}

	public void updateSeq(Long seq) {
		this.seq = seq;
	}

	public void updateInfo(String info) {
		this.info = info;
	}

	public void updateIsImage(Boolean isImage) {
		this.isImage = isImage;
	}
}
