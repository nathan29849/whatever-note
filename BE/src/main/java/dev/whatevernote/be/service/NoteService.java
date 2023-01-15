package dev.whatevernote.be.service;

import dev.whatevernote.be.exception.not_found.NotFoundNoteException;
import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
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
public class NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
	private static final int DEFAULT_RANGE = 1_000;
	private final NoteRepository noteRepository;
	private final CardRepository cardRepository;
	private final ContentRepository contentRepository;

	public NoteService(NoteRepository noteRepository,
		CardRepository cardRepository,
		ContentRepository contentRepository) {
		this.noteRepository = noteRepository;
		this.cardRepository = cardRepository;
		this.contentRepository = contentRepository;
	}

	public NoteResponseDto findById(final Integer noteId) {
		Note note = findNoteById(noteId);
		return NoteResponseDto.from(note);
	}

	private Note findNoteById(Integer noteId) {
		return noteRepository.findById(noteId)
			.orElseThrow(NotFoundNoteException::new);
	}


	@Transactional
	public NoteResponseDto create(NoteRequestDto noteRequestDto) {
		noteRequestDto = editSeq(noteRequestDto);
		final Note savedNote = noteRepository.save(Note.from(noteRequestDto));
		logger.debug("[CREATE Note] ID = {}, SEQ = {}", savedNote.getId(), savedNote.getSeq());
		return NoteResponseDto.from(savedNote);
	}

	public NoteResponseDtos findAll(Pageable pageable) {
		Slice<Note> notes = noteRepository.findAllByOrderBySeq(pageable);
		return NoteResponseDtos.from(notes);
	}

	@Transactional
	public NoteResponseDto update(Integer updateNoteId, NoteRequestDto noteRequestDto) {
		Note note = findNoteById(updateNoteId);
		logger.debug("현재 노트의 Seq={}", note.getSeq());

		if (noteRequestDto.getTitle() != null) {
			note.updateTitle(noteRequestDto.getTitle());
		} else {
			List<Note> notes = noteRepository.findAllByOrderBySeq();
			int idx = notes.indexOf(note);
			if (noteRequestDto.getSeq() == idx + 1) {
				return NoteResponseDto.from(note);
			}
			logger.debug("이전 노트의 개수 = {}, 현재 NoteRequestDto의 Seq ={}", notes.indexOf(note), noteRequestDto.getSeq());
			note.updateSeq(editSeq(noteRequestDto));
		}
		return NoteResponseDto.from(note);
	}

	private NoteRequestDto editSeq(NoteRequestDto noteRequestDto) {
		Integer noteDtoSeq = noteRequestDto.getSeq();
		if (noteDtoSeq == null || noteDtoSeq == 0) {
			return getNoteRequestDtoWithFirstSeq(noteRequestDto);
		}
		return getNoteRequestDto(noteRequestDto);
	}

	private NoteRequestDto getNoteRequestDtoWithFirstSeq(NoteRequestDto noteRequestDto) {
		String noteDtoTitle = noteRequestDto.getTitle();
		Optional<Note> note = noteRepository.findFirstByOrderBySeq();
		return note.map(value -> new NoteRequestDto(value.getSeq() / 2, noteDtoTitle))
			.orElseGet(() -> new NoteRequestDto(DEFAULT_RANGE, noteDtoTitle));
	}

	private NoteRequestDto getNoteRequestDto(NoteRequestDto noteRequestDto) {
		Integer noteDtoSeq = noteRequestDto.getSeq();
		List<Note> notes = noteRepository.findAllByOrderBySeq();
		if (notes.isEmpty()) {
			return new NoteRequestDto(DEFAULT_RANGE, noteRequestDto.getTitle());
		}

		if (notes.size() > noteDtoSeq){
			Integer seq = notes.get(noteDtoSeq).getSeq();
			Integer preSeq = notes.get(noteDtoSeq -1).getSeq();
			logger.debug("seq={}, preSeq={}, updateSeq={}", seq, preSeq, (seq+preSeq)/2);
			return new NoteRequestDto((seq + preSeq) / 2, noteRequestDto.getTitle());
		}

		return new NoteRequestDto((notes.size() + 1) * DEFAULT_RANGE, noteRequestDto.getTitle());
	}

	@Transactional
	public void delete(Integer noteId) {
		Note note = findNoteById(noteId);
		List<Card> cards = cardRepository.findAllByNoteId(noteId);
		for (Card card : cards) {
			contentRepository.deleteAll(card.getId());
			logger.debug("[CONTENT ALL DELETED] (CARD ID = {})'s contents delete", card.getId());
		}
		cardRepository.deleteAll(noteId);
		logger.debug("[CARD ALL DELETED] (NOTE ID = {})'s cards delete", note.getId());
		noteRepository.delete(note);
		logger.debug("[NOTE DELETED] NOTE ID = {}", note.getId());
	}


}
