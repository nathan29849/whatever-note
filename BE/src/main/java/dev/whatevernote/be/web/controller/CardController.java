package dev.whatevernote.be.web.controller;

import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<CardResponseDto> create(@RequestBody final CardRequestDto cardRequestDto,
		@PathVariable final Integer noteId) {
		return ResponseEntity.ok(cardService.create(cardRequestDto, noteId));
	}

	@GetMapping
	public ResponseEntity<CardResponseDtos> findAll(final Pageable pageable, @PathVariable final Integer noteId) {
		return ResponseEntity.ok(cardService.findAll(pageable, noteId));
	}
}
