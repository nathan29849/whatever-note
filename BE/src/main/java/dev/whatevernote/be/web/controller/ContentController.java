package dev.whatevernote.be.web.controller;

import dev.whatevernote.be.common.BaseResponse;
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
	public BaseResponse<ContentResponseDto> create(@RequestBody final ContentRequestDto contentRequestDto, @PathVariable final Long cardId) {
		ContentResponseDto contentResponseDto = contentService.create(contentRequestDto, cardId);
		return new BaseResponse("code", "message", contentResponseDto);
	}

	@GetMapping
	public BaseResponse<ContentResponseDto> findAll(final Pageable pageable, @PathVariable final Long cardId) {
		ContentResponseDtos contentResponseDtos = contentService.findAll(pageable, cardId);
		return new BaseResponse("code", "message", contentResponseDtos);
	}

	@GetMapping("/{contentId}")
	public BaseResponse<ContentResponseDto> findById(@PathVariable final Long contentId) {
		ContentResponseDto contentResponseDto = contentService.findById(contentId);
		return new BaseResponse("code", "message", contentResponseDto);
	}

	@PutMapping("/{contentId}")
	public BaseResponse<ContentResponseDto> update(@PathVariable final Long cardId,
		@PathVariable final Long contentId,
		@RequestBody final ContentRequestDto contentRequestDto) {
		ContentResponseDto contentResponseDto = contentService.update(cardId, contentId, contentRequestDto);
		return new BaseResponse("code", "message", contentResponseDto);
	}

	@DeleteMapping("{contentId}")
	public BaseResponse<Void> delete(@PathVariable final Long contentId) {
		contentService.delete(contentId);
		return new BaseResponse("code", "message", null);
	}

}
