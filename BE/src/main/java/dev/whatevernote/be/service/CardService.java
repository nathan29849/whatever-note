package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.response.CardDetailResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CardService {

	private static final Logger logger = LoggerFactory.getLogger(CardService.class);
	private static final long DEFAULT_RANGE = 1_000L;
	private static final String NOT_FOUNT_ID = "존재하지 않는 ID 입니다.";

	private final CardRepository cardRepository;
	private final NoteRepository noteRepository;
	private final ContentRepository contentRepository;

	public CardService(CardRepository cardRepository, NoteRepository noteRepository, ContentRepository contentRepository) {
		this.cardRepository = cardRepository;
		this.noteRepository = noteRepository;
		this.contentRepository = contentRepository;
	}

	@Transactional
	public CardResponseDto create(CardRequestDto cardRequestDto, Integer noteId) {
		cardRequestDto = editSeq(cardRequestDto, noteId);
		Note note = findNoteById(noteId);
		final Card savedCard = cardRepository.save(
			Card.from(cardRequestDto, note)
		);
		logger.debug("[CREATE Card] ID = {}, SEQ = {}, TITLE = {}, Note ID = {}", savedCard.getId(),
			savedCard.getSeq(), savedCard.getTitle(), note.getId());
		return CardResponseDto.from(savedCard, noteId);
	}

	private Note findNoteById(Integer noteId) {
		return noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));
	}

	private CardRequestDto editSeq(CardRequestDto cardRequestDto, Integer noteId) {
		Long cardDtoSeq = cardRequestDto.getSeq();
		if (cardDtoSeq == null || cardDtoSeq == 0) {
			return getCardRequestDtoWithFirstSeq(cardRequestDto, noteId);
		}
		return getCardRequestDto(cardRequestDto, noteId);
	}

	private CardRequestDto getCardRequestDtoWithFirstSeq(CardRequestDto cardRequestDto, Integer noteId) {
		List<Card> cards = getCardsByNoteId(noteId);

		if (cards.isEmpty()) {
			return new CardRequestDto(DEFAULT_RANGE, cardRequestDto.getTitle());
		}
		return new CardRequestDto(cards.get(0).getSeq() / 2, cardRequestDto.getTitle());
	}

	private CardRequestDto getCardRequestDto(CardRequestDto cardRequestDto, Integer noteId) {
		Long cardDtoSeq = cardRequestDto.getSeq();
		List<Card> cards = getCardsByNoteId(noteId);
		if (cards.isEmpty()) {
			return new CardRequestDto(DEFAULT_RANGE, cardRequestDto.getTitle());
		}

		if (cards.size() > cardDtoSeq) {
			Long seq = cards.get(cardDtoSeq.intValue()).getSeq();
			Long preSeq = cards.get((int) (cardDtoSeq - 1)).getSeq();
			logger.debug("seq={}, preSeq={}, updateSeq={}", seq, preSeq, (seq + preSeq) / 2);
			return new CardRequestDto((seq + preSeq) / 2, cardRequestDto.getTitle());
		}

		return new CardRequestDto((cards.size() + 1) * DEFAULT_RANGE, cardRequestDto.getTitle());
	}

	private List<Card> getCardsByNoteId(Integer noteId) {
		List<Card> cards = cardRepository.findAllByNoteId(noteId);
		cards.sort(
			(o1, o2) -> (int) (o1.getSeq() - o2.getSeq())
		);
		return cards;
	}

	public CardResponseDtos findAll(final Pageable pageable, Integer noteId) {
		Note note = findNoteById(noteId);
		Slice<Card> cards = cardRepository.findAllByNoteOrderBySeq(pageable, note);
		logger.debug("Now Page Number = {}, has Next = {}", cards.getNumber(), cards.hasNext());
		return CardResponseDtos.from(cards, noteId);
	}

	public CardDetailResponseDto findById(Integer noteId, Long cardId) {
		Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));
		List<Content> contents = findContentsById(cardId);
		return CardDetailResponseDto.from(card, noteId, contents);
	}

	private List<Content> findContentsById(Long cardId) {
		List<Content> contents = contentRepository.findAllByCardId(cardId);
		contents.sort((o1, o2) -> (int) (o1.getSeq() - o2.getSeq()));
		return contents;
	}

	@Transactional
	public CardResponseDto update(Integer noteId, Long cardId, CardRequestDto cardRequestDto) {
		Card card = findByCardId(cardId);
		// TODO
		// 해당 카드가 노트와 연관이 있는 것인지 검증

		if (cardRequestDto.getTitle() != null) {
			card.updateTitle(cardRequestDto.getTitle());
		} else {
			List<Card> cards = getCardsByNoteId(noteId);
			if (cards.indexOf(card) != cardRequestDto.getSeq()) {
				card.updateSeq(editSeq(cardRequestDto, noteId).getSeq());
			}
		}

		logger.debug("[CARD UPDATE] card id = {}, note id = {}, title = {}, seq = {}",
			cardId, noteId, card.getTitle(), card.getSeq());
		return CardResponseDto.from(card, noteId);
	}

	private Card findByCardId(Long cardId) {
		return cardRepository.findById(cardId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));
	}
}
