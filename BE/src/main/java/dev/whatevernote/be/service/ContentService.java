package dev.whatevernote.be.service;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import java.util.List;
import java.util.Optional;
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
		contentRequestDto = editSeq(contentRequestDto, cardId);
		Card card = cardRepository.findById(cardId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));

		Content content = Content.from(contentRequestDto, card);
		final Content savedContent = contentRepository.save(content);

		logger.debug("[CREATE Content] ID = {}, SEQ = {}, INFO = {}, IS_IMAGE={}, CARD ID = {}",
			savedContent.getId(), savedContent.getSeq(), savedContent.getInfo(), savedContent.getIsImage(),
			savedContent.getCard().getId());

		return ContentResponseDto.from(savedContent);
	}

	private ContentRequestDto editSeq(ContentRequestDto contentRequestDto, Long cardId) {
		Long contentDtoSeq = contentRequestDto.getSeq();
		if (contentDtoSeq == null || contentDtoSeq == 0) {
			return getContentRequestDtoWithFirstSeq(contentRequestDto);
		}
		return getContentRequestDto(contentRequestDto, cardId);
	}

	private ContentRequestDto getContentRequestDtoWithFirstSeq(ContentRequestDto contentRequestDto) {
		return contentRepository.findFirstByOrderBySeq()
			.map(c -> new ContentRequestDto(c.getInfo(), c.getSeq() / 2, c.getIsImage()))
			.orElseGet(() -> new ContentRequestDto(contentRequestDto.getInfo(), DEFAULT_RANGE,
				contentRequestDto.getIsImage()));
	}

	private ContentRequestDto getContentRequestDto(ContentRequestDto contentRequestDto, Long cardId) {
		List<Content> contents = contentRepository.findAllByCardId(cardId);
		contents.sort((o1, o2) -> (int) (o1.getSeq() - o2.getSeq()));

		if (contents.isEmpty()) {
			return new ContentRequestDto(contentRequestDto.getInfo(), DEFAULT_RANGE, contentRequestDto.getIsImage());
		}

		Long contentDtoSeq = contentRequestDto.getSeq();
		if (contents.size() > contentDtoSeq) {
			Long seq = contents.get(contentDtoSeq.intValue()).getSeq();
			Long preSeq = contents.get((int) (contentDtoSeq - 1)).getSeq();
			logger.debug("seq={}, preSeq={}, updateSeq={}", seq, preSeq, (seq + preSeq) / 2);
			return new ContentRequestDto(contentRequestDto.getInfo(), (seq + preSeq) / 2, contentRequestDto.getIsImage());
		}
		return new ContentRequestDto(contentRequestDto.getInfo(),
			(contents.size() + 1) * DEFAULT_RANGE, contentRequestDto.getIsImage());
	}

	public ContentResponseDto findById(Long contentId) {

		Content content = contentRepository.findById(contentId)
			.orElseThrow(() -> new IllegalArgumentException(NOT_FOUNT_ID));

		return ContentResponseDto.from(content);
	}
}
