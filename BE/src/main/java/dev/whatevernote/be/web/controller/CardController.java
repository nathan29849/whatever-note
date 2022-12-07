package dev.whatevernote.be.web.controller;

import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note/{noteId}/card")
public class CardController {

	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@PostMapping
	public BaseResponse<CardResponseDto> create(@RequestBody final CardRequestDto cardRequestDto, @PathVariable final Integer noteId) {
		CardResponseDto cardResponseDto = cardService.create(cardRequestDto, noteId);
		return new BaseResponse("code", "message", cardResponseDto);
	}

	@GetMapping("/{cardId}")
	public BaseResponse<CardResponseDto> findById(@PathVariable final Integer noteId, @PathVariable final Long cardId) {
		CardResponseDto cardResponseDto = cardService.findById(noteId, cardId);
		return new BaseResponse("code", "message", cardResponseDto);
	}

	@GetMapping
	public BaseResponse<CardResponseDtos> findAll(final Pageable pageable, @PathVariable final Integer noteId) {
		CardResponseDtos cardResponseDtos = cardService.findAll(pageable, noteId);
		return new BaseResponse("code", "message", cardResponseDtos);
	}
}
