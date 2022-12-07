package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Card;

public class CardResponseDto {

	private final Long id;
	private final String title;
	private final Long seq;
	private final Integer noteId;

	public CardResponseDto(Long id, String title, Long seq, Integer noteId) {
		this.id = id;
		this.title = title;
		this.seq = seq;
		this.noteId = noteId;
	}

	public static CardResponseDto from(Card card, int noteId) {
		return new CardResponseDto(card.getId(), card.getTitle(), card.getSeq(), noteId);
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Long getSeq() {
		return seq;
	}

	public Integer getNoteId() {
		return noteId;
	}
}
