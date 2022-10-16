package dev.whatevernote.be.hello.web.controller;

import dev.whatevernote.be.hello.service.NoteService;
import dev.whatevernote.be.hello.service.dto.response.NoteResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/note")
@RestController
public class NoteController {

	private final NoteService noteService;


	public NoteController(final NoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping("/{noteId}")
	public ResponseEntity<NoteResponseDto> findById(@PathVariable final Long noteId) {
		return ResponseEntity.ok(noteService.findById(noteId));
	}

}
