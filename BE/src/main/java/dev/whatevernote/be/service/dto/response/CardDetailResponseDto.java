package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import java.util.List;
import java.util.stream.Collectors;

public class CardDetailResponseDto {

	private final Long cardId;
	private final Long cardSeq;
	private final String cardTitle;
	private final Integer noteId;
	private final List<ContentResponseDto> contents;

	public CardDetailResponseDto(Long cardId, Long cardSeq, String cardTitle, Integer noteId,
		List<ContentResponseDto> contents) {
		this.cardId = cardId;
		this.cardSeq = cardSeq;
		this.cardTitle = cardTitle;
		this.noteId = noteId;
		this.contents = contents;
	}

	public Long getCardId() {
		return cardId;
	}

	public Long getCardSeq() {
		return cardSeq;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public List<ContentResponseDto> getContents() {
		return contents;
	}

	public static CardDetailResponseDto from(Card card, Integer noteId, List<Content> contents) {
		CardResponseDto cardDto = CardResponseDto.from(card, noteId);
		return new CardDetailResponseDto(
			cardDto.getId(),
			cardDto.getSeq(),
			cardDto.getTitle(),
			cardDto.getNoteId(),
			contents.stream()
				.map(ContentResponseDto::from)
				.collect(Collectors.toList())
		);
	}


}
