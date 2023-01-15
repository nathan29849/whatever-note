package dev.whatevernote.be.web.controller;

import static dev.whatevernote.be.common.ResponseCodeAndMessages.NOTE_CREATE_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.NOTE_MODIFY_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.NOTE_REMOVE_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.NOTE_RETRIEVE_ALL_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.NOTE_RETRIEVE_DETAIL_SUCCESS;

import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import org.springframework.data.domain.Pageable;
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
	public BaseResponse<NoteResponseDto> findById(@PathVariable final Integer noteId) {
		return new BaseResponse<>(NOTE_RETRIEVE_DETAIL_SUCCESS, noteService.findById(noteId));
	}

	@GetMapping
	public BaseResponse<NoteResponseDtos> findAll(final Pageable pageable) {
		return new BaseResponse<>(NOTE_RETRIEVE_ALL_SUCCESS, noteService.findAll(pageable));
	}

	@PostMapping
	public BaseResponse<NoteResponseDto> create(@RequestBody final NoteRequestDto noteRequestDto) {
		return new BaseResponse<>(NOTE_CREATE_SUCCESS, noteService.create(noteRequestDto));
	}

	@PutMapping("/{noteId}")
	public BaseResponse<NoteResponseDto> update(@PathVariable final Integer noteId,
		@RequestBody final NoteRequestDto noteRequestDto) {
		return new BaseResponse<>(NOTE_MODIFY_SUCCESS, noteService.update(noteId, noteRequestDto));
	}

	@DeleteMapping("/{noteId}")
	public BaseResponse<Void> delete(@PathVariable final Integer noteId) {
		noteService.delete(noteId);
		return new BaseResponse<>(NOTE_REMOVE_SUCCESS, null);
	}

}
