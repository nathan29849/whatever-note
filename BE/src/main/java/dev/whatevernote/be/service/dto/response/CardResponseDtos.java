package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Card;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Slice;

public class CardResponseDtos {

	private final List<CardResponseDto> cards;
	private final boolean hasNext;
	private final int pageNumber;

	public CardResponseDtos(List<CardResponseDto> cardResponseDtos, boolean hasNext, int pageNumber) {
		this.cards = cardResponseDtos;
		this.hasNext = hasNext;
		this.pageNumber = pageNumber;
	}

	public List<CardResponseDto> getCards() {
		return cards;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public static CardResponseDtos from(Slice<Card> cards, int noteId) {
		return new CardResponseDtos(
			cards.stream()
				.map(c -> CardResponseDto.from(c, noteId))
				.collect(Collectors.toList()),
			cards.hasNext(),
			cards.getNumber());
	}
}
