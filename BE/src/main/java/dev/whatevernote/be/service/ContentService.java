package dev.whatevernote.be.service;

import dev.whatevernote.be.exception.not_found.NotFoundCardException;
import dev.whatevernote.be.exception.not_found.NotFoundContentException;
import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDtos;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
	private static final long DEFAULT_RANGE = 1_000L;
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
			.orElseThrow(NotFoundCardException::new);

		Content content = Content.from(contentRequestDto, card);
		final Content savedContent = contentRepository.save(content);

		logger.info("[CREATE Content] ID = {}, SEQ = {}, INFO = {}, IS_IMAGE={}, CARD ID = {}",
			savedContent.getId(), savedContent.getSeq(), savedContent.getInfo(), savedContent.getIsImage(),
			savedContent.getCard().getId());

		return ContentResponseDto.from(savedContent);
	}

	private ContentRequestDto editSeq(ContentRequestDto contentRequestDto, Long cardId) {
		Long contentDtoSeq = contentRequestDto.getSeq();
		if (contentDtoSeq == null || contentDtoSeq == 0L) {
			return getContentRequestDtoWithFirstSeq(contentRequestDto, cardId);
		}
		return getContentRequestDto(contentRequestDto, cardId);
	}

	private ContentRequestDto getContentRequestDtoWithFirstSeq(ContentRequestDto contentRequestDto, Long cardId) {
		List<Content> contents = contentRepository.findAllByCardId(cardId);
		if (contents.isEmpty()) {
			return new ContentRequestDto(contentRequestDto.getInfo(), DEFAULT_RANGE,
				contentRequestDto.getIsImage());
		}
		contents.sort((o1, o2) -> Math.toIntExact(o1.getSeq() - o2.getSeq()));
		Content content = contents.get(0);
		return new ContentRequestDto(contentRequestDto.getInfo(), content.getSeq() / 2,
			contentRequestDto.getIsImage());
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
			.orElseThrow(NotFoundContentException::new);

		return ContentResponseDto.from(content);
	}

	public ContentResponseDtos findAll(Pageable pageable, Long cardId) {

		Slice<Content> contents = contentRepository.findAllByCardIdOrderBySeq(pageable, cardId);
		return ContentResponseDtos.from(contents);
	}

	@Transactional
	public ContentResponseDto update(Long cardId, Long contentId, ContentRequestDto contentRequestDto) {

		Content content = contentRepository.findById(contentId)
			.orElseThrow(NotFoundContentException::new);

		if (contentRequestDto.getSeq() != null) {
			List<Content> contents = contentRepository.findAllByCardIdOrderBySeq(cardId);
			if (contents.indexOf(content) + 1 == contentRequestDto.getSeq()) {
				return ContentResponseDto.from(content);
			}
			contentRequestDto = editSeq(contentRequestDto, content.getCard().getId());
			content.updateSeq(contentRequestDto.getSeq());
			return ContentResponseDto.from(content);
		}

		if (contentRequestDto.getInfo() != null && !contentRequestDto.getInfo().isEmpty()) {
			if (contentRequestDto.getIsImage() != null && contentRequestDto.getIsImage() == Boolean.TRUE) {
				content.updateIsImage(contentRequestDto.getIsImage());
			}
			content.updateInfo(contentRequestDto.getInfo());
			return ContentResponseDto.from(content);
		}

		return ContentResponseDto.from(content);
	}

	@Transactional
	public void delete(Long contentId) {

		contentRepository.deleteById(contentId);
		logger.debug("[CONTENT DELETED] content id = {}", contentId);
	}
}
