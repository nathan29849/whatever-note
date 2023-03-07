package dev.whatevernote.be.service;

import dev.whatevernote.be.exception.bad_request.NotMatchLoginMember;
import dev.whatevernote.be.exception.not_found.NotFoundCardException;
import dev.whatevernote.be.exception.not_found.NotFoundNoteException;
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
import java.util.Objects;
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
	private final CardRepository cardRepository;
	private final NoteRepository noteRepository;
	private final ContentRepository contentRepository;

	public CardService(CardRepository cardRepository, NoteRepository noteRepository, ContentRepository contentRepository) {
		this.cardRepository = cardRepository;
		this.noteRepository = noteRepository;
		this.contentRepository = contentRepository;
	}

	private static void checkValidMember(Long memberId, Note note) {
		if (!Objects.equals(note.getMember().getId(), memberId)) {
			throw new NotMatchLoginMember();
		}
	}

	@Transactional
	public CardResponseDto create(CardRequestDto cardRequestDto, Integer noteId, Long memberId) {
		cardRequestDto = editSeq(cardRequestDto, noteId);
		Note note = findNoteById(noteId);
		checkValidMember(memberId, note);
		final Card savedCard = cardRepository.save(
			Card.from(cardRequestDto, note)
		);
		logger.debug("[CREATE Card] ID = {}, SEQ = {}, TITLE = {}, Note ID = {}", savedCard.getId(),
			savedCard.getSeq(), savedCard.getTitle(), note.getId());
		return CardResponseDto.from(savedCard);
	}

	private Note findNoteById(Integer noteId) {
		return noteRepository.findById(noteId)
			.orElseThrow(NotFoundNoteException::new);
	}

	private CardRequestDto editSeq(CardRequestDto cardRequestDto, Integer noteId) {
		Long cardDtoSeq = cardRequestDto.getSeq();
		if (cardDtoSeq == null || cardDtoSeq == 0L) {
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
		return cardRepository.findAllByNoteIdOrderBySeq(noteId);
	}

	public CardResponseDtos findAll(final Pageable pageable, Integer noteId, Long memberId) {
		Note note = findNoteById(noteId);
		checkValidMember(memberId, note);
		Slice<Card> cards = cardRepository.findAllByNoteIdOrderBySeqAsc(pageable, noteId);
		logger.debug("Now Page Number = {}, has Next = {}", cards.getNumber(), cards.hasNext());
		return CardResponseDtos.from(cards, noteId);
	}

	public CardDetailResponseDto findById(Integer noteId, Long cardId, Long memberId) {
		Note note = findNoteById(noteId);
		checkValidMember(memberId, note);

		Card card = cardRepository.findById(cardId)
			.orElseThrow(NotFoundCardException::new);
		List<Content> contents = findContentsById(cardId);
		return CardDetailResponseDto.from(card, noteId, contents);
	}

	private List<Content> findContentsById(Long cardId) {
		return contentRepository.findAllByCardIdOrderBySeqAsc(cardId);
	}

	@Transactional
	public CardResponseDto update(Integer noteId, Long cardId, CardRequestDto cardRequestDto,
		Long memberId) {
		Note note = findNoteById(noteId);
		checkValidMember(memberId, note);
		Card card = findByCardId(cardId);
		logger.info("[BEFORE CARD UPDATE] card id = {}, note id = {}, title = {}, seq = {}",
			cardId, noteId, card.getTitle(), card.getSeq());
		// TODO
		// 해당 카드가 노트와 연관이 있는 것인지 검증

		if (cardRequestDto.getTitle() != null) {
			card.updateTitle(cardRequestDto.getTitle());
		} else {
			List<Card> cards = getCardsByNoteId(noteId);
			int idx = cards.indexOf(card);
			if (cardRequestDto.getSeq() == idx+1) {
				return CardResponseDto.from(card);
			}

			card.updateSeq(editSeq(cardRequestDto, noteId).getSeq());
		}

		logger.info("[AFTER CARD UPDATE] card id = {}, note id = {}, title = {}, seq = {}",
			cardId, noteId, card.getTitle(), card.getSeq());
		return CardResponseDto.from(card);
	}

	private Card findByCardId(Long cardId) {
		return cardRepository.findById(cardId)
			.orElseThrow(NotFoundCardException::new);
	}

	@Transactional
	public void delete(Integer noteId, Long cardId, Long memberId) {
		Note note = findNoteById(noteId);
		checkValidMember(memberId, note);
		Card card = findByCardId(cardId);
		contentRepository.deleteAll(cardId);
		logger.debug("[CONTENT ALL DELETED] (CARD ID = {})'s contents delete", card.getId());

		cardRepository.delete(card);
		logger.debug("[CARD DELETED] CARD ID = {}", card.getId());
	}
}
