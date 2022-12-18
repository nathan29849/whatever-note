package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
	private static final long DEFAULT_RANGE = 1_000L;
	private static final String NOT_FOUNT_ID = "존재하지 않는 ID 입니다.";

	private final CardRepository cardRepository;
	private final ContentRepository contentRepository;

	public ContentService(CardRepository cardRepository, ContentRepository contentRepository) {

		this.cardRepository = cardRepository;
		this.contentRepository = contentRepository;
	}

	@Transactional
	public ContentResponseDto create(ContentRequestDto contentRequestDto, Long cardId) {

		Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));

		Content content = Content.from(contentRequestDto, card);
		final Content savedContent = contentRepository.save(content);

		logger.debug("[CREATE Content] ID = {}, SEQ = {}, INFO = {}, IS_IMAGE={}, CARD ID = {}",
			savedContent.getId(), savedContent.getSeq(), savedContent.getInfo(), savedContent.getIsImage(),
			savedContent.getCard().getId());

		return ContentResponseDto.from(savedContent);
	}

}
