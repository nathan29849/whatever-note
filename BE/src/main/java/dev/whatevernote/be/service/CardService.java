package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CardService {

	private static final Logger logger = LoggerFactory.getLogger(CardService.class);
	private static final long DEFAULT_RANGE = 1_000L;

	private final CardRepository cardRepository;

	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Transactional
	public CardResponseDto create(CardRequestDto cardRequestDto) {
		cardRequestDto = editSeq(cardRequestDto);
		final Card savedCard = cardRepository.save(Card.from(cardRequestDto));
		logger.debug("[CREATE Card] ID = {}, SEQ = {}", savedCard.getId(), savedCard.getSeq());
		return CardResponseDto.from(savedCard);
	}

	private CardRequestDto editSeq(CardRequestDto cardRequestDto) {
		Long cardDtoSeq = cardRequestDto.getSeq();
		if (cardDtoSeq == null || cardDtoSeq == 0) {
			return getNoteRequestDtoWithFirstSeq(cardRequestDto);
		}
		return getCardRequestDto(cardRequestDto);
	}

	private CardRequestDto getNoteRequestDtoWithFirstSeq(CardRequestDto cardRequestDto) {
		String noteDtoTitle = cardRequestDto.getTitle();
		Optional<Card> card = cardRepository.findFirstByOrderBySeq();
		return card.map(value -> new CardRequestDto(value.getSeq() / 2, noteDtoTitle))
			.orElseGet(() -> new CardRequestDto(DEFAULT_RANGE, noteDtoTitle));
	}

	private CardRequestDto getCardRequestDto(CardRequestDto cardRequestDto) {
		Long cardDtoSeq = cardRequestDto.getSeq();
		List<Card> cards = cardRepository.findAllByOrderBySeq();
		if (cards.isEmpty()) {
			return new CardRequestDto(DEFAULT_RANGE, cardRequestDto.getTitle());
		}

		if (cards.size() > cardDtoSeq){
			Long seq = cards.get(cardDtoSeq.intValue()).getSeq();
			Long preSeq = cards.get((int) (cardDtoSeq -1)).getSeq();
			logger.debug("seq={}, preSeq={}, updateSeq={}", seq, preSeq, (seq+preSeq)/2);
			return new CardRequestDto((seq + preSeq) / 2, cardRequestDto.getTitle());
		}

		return new CardRequestDto((cards.size() + 1) * DEFAULT_RANGE, cardRequestDto.getTitle());
	}
}
