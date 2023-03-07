package dev.whatevernote.be.web.controller;

import static dev.whatevernote.be.common.ResponseCodeAndMessages.CONTENT_CREATE_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.CONTENT_MODIFY_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.CONTENT_REMOVE_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.CONTENT_RETRIEVE_ALL_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.CONTENT_RETRIEVE_DETAIL_SUCCESS;

import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.login.Login;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDtos;
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
@RequestMapping("/api/note/{noteId}/card/{cardId}/content")
public class ContentController {

	private final ContentService contentService;

	public ContentController(ContentService contentService) {
		this.contentService = contentService;
	}

	@PostMapping
	public BaseResponse<ContentResponseDto> create(@RequestBody final ContentRequestDto contentRequestDto,
		@PathVariable final Integer noteId, @PathVariable final Long cardId, @Login final Long memberId) {
		ContentResponseDto contentResponseDto = contentService.create(contentRequestDto, noteId, cardId, memberId);
		return new BaseResponse<>(CONTENT_CREATE_SUCCESS, contentResponseDto);
	}

	@GetMapping
	public BaseResponse<ContentResponseDtos> findAll(final Pageable pageable, @PathVariable final Integer noteId,
		@PathVariable final Long cardId, @Login final Long memberId) {
		ContentResponseDtos contentResponseDtos = contentService.findAll(pageable, noteId, cardId, memberId);
		return new BaseResponse<>(CONTENT_RETRIEVE_ALL_SUCCESS, contentResponseDtos);
	}

	@GetMapping("/{contentId}")
	public BaseResponse<ContentResponseDto> findById(@PathVariable final Integer noteId,
		@PathVariable final Long contentId, @Login final Long memberId) {
		ContentResponseDto contentResponseDto = contentService.findById(noteId, contentId, memberId);
		return new BaseResponse<>(CONTENT_RETRIEVE_DETAIL_SUCCESS, contentResponseDto);
	}

	@PutMapping("/{contentId}")
	public BaseResponse<ContentResponseDto> update(@PathVariable final Integer noteId, @PathVariable final Long cardId,
		@PathVariable final Long contentId, @RequestBody final ContentRequestDto contentRequestDto, @Login final Long memberId) {
		ContentResponseDto contentResponseDto = contentService.update(noteId, cardId, contentId, contentRequestDto, memberId);
		return new BaseResponse<>(CONTENT_MODIFY_SUCCESS, contentResponseDto);
	}

	@DeleteMapping("{contentId}")
	public BaseResponse<Void> delete(@PathVariable final Integer noteId, @PathVariable final Long contentId, @Login final Long memberId) {
		contentService.delete(noteId, contentId, memberId);
		return new BaseResponse<>(CONTENT_REMOVE_SUCCESS, null);
	}

}
