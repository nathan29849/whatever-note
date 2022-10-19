package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

	private final NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	public NoteResponseDto findById(final Integer noteId) {
		return null;
	}

	@Transactional
	public NoteResponseDto create(final NoteRequestDto noteRequestDto) {
		final Note savedNote = noteRepository.save(Note.from(noteRequestDto));
		return NoteResponseDto.from(savedNote);
	}
}
