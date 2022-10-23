package dev.whatevernote.be.web.controller;

import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Void> create(@RequestBody NoteRequestDto noteRequestDto) {
		final NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);
		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
			.location(URI.create("/api/note/" + noteResponseDto.getId())).build();
	}

}
