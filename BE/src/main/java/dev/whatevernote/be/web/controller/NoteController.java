package dev.whatevernote.be.web.controller;

import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
public class NoteController {

	private final NoteService noteService;

	public NoteController(final NoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping("/{noteId}")
	public ResponseEntity<NoteResponseDto> findById(@PathVariable final Integer noteId) {
		return ResponseEntity.ok(noteService.findById(noteId));
	}

	@GetMapping
	public ResponseEntity<NoteResponseDtos> findAll(final Pageable pageable) {
		return ResponseEntity.ok(noteService.findAll(pageable));
	}

	@PostMapping
	public ResponseEntity<NoteResponseDto> create(@RequestBody final NoteRequestDto noteRequestDto) {
		return ResponseEntity.ok(noteService.create(noteRequestDto));
	}

	@PutMapping("/{noteId}")
	public ResponseEntity<NoteResponseDto> update(@PathVariable final Integer noteId,
		@RequestBody final NoteRequestDto noteRequestDto) {
		return ResponseEntity.ok(noteService.update(noteId, noteRequestDto));
	}

	@DeleteMapping("/{noteId}")
	public ResponseEntity<Void> delete(@PathVariable final Integer noteId) {
		noteService.delete(noteId);
		return ResponseEntity.ok().build();
	}

}
