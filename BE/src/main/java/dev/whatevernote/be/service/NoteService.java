package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.NoteRepository;
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

@Service
public class NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
	private static final int DEFAULT_RANGE = 1_000;

	private final NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@Transactional(readOnly = true)
	public NoteResponseDto findById(final Integer noteId) {
		Note note = noteRepository.findById(noteId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
		return NoteResponseDto.from(note);
	}

	@Transactional
	public NoteResponseDto create(NoteRequestDto noteRequestDto) {

		if (noteRequestDto.getSeq() == null || noteRequestDto.getSeq() == 0) {
			Optional<Note> note = noteRepository.findFirstByOrderBySeq();
			if (note.isPresent()) {
				noteRequestDto = new NoteRequestDto(note.get().getSeq() / 2, noteRequestDto.getTitle());
			} else {
				noteRequestDto = new NoteRequestDto(DEFAULT_RANGE, noteRequestDto.getTitle());
			}
		} else {
			List<Note> notes = noteRepository.findAllByOrderBySeq();
			Integer requestSeq = noteRequestDto.getSeq();
			if (notes.size() >= 1) {
				if (notes.size() > requestSeq){
					Integer seq = notes.get(requestSeq).getSeq();
					Integer preSeq = notes.get(requestSeq-1).getSeq();
					noteRequestDto = new NoteRequestDto((seq+preSeq) / 2, noteRequestDto.getTitle());
				} else {
					noteRequestDto = new NoteRequestDto((notes.size()+1) * DEFAULT_RANGE, noteRequestDto.getTitle());
				}
			} else {
				noteRequestDto = new NoteRequestDto(DEFAULT_RANGE, noteRequestDto.getTitle());
			}
		}

		final Note savedNote = noteRepository.save(Note.from(noteRequestDto));
		logger.debug("[CREATE Note] ID = {}, SEQ = {}", savedNote.getId(), savedNote.getSeq());
		return NoteResponseDto.from(savedNote);
	}

	@Transactional(readOnly = true)
	public NoteResponseDtos findAll(Pageable pageable) {
		Slice<Note> notes = noteRepository.findAllByOrderBySeq(pageable);
		return NoteResponseDtos.from(notes);
	}
}
