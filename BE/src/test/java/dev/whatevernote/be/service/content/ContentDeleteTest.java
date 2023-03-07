package dev.whatevernote.be.service.content;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.whatevernote.be.exception.ErrorCodeAndMessages;
import dev.whatevernote.be.exception.not_found.NotFoundContentException;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.domain.Content;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Content 삭제")
class ContentDeleteTest extends InitIntegrationTest {

	private static final Long CARD_ID = 1L;
	private static final Long CONTENT_ID = 1L;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Nested
	@DisplayName("컨텐트를 삭제할 때")
	class DeleteTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalDeleteTest {

			@DisplayName("주어진 ID의 컨텐트를 삭제 상태로 바꾼다.")
			@Test
			void soft_delete_content() {
				//given
				List<Content> contents = contentRepository.findAllByCardIdOrderBySeqAsc(CARD_ID);
				int numberOfContent = contents.size();

				//when
				contentService.delete(FIRST_NOTE_ID, CONTENT_ID, MEMBER_ID);
				Optional<Content> content = contentRepository.findById(CONTENT_ID);
				List<Content> afterDelete = contentRepository.findAllByCardIdOrderBySeqAsc(CARD_ID);

				//then
				assertThat(content).isEmpty();
				assertThat(afterDelete).hasSize(numberOfContent - 1);
				assertThatThrownBy(() -> contentService.findById(FIRST_NOTE_ID, CONTENT_ID, MEMBER_ID))
					.isInstanceOf(NotFoundContentException.class)
					.hasMessageContaining(ErrorCodeAndMessages.E404_NOT_FOUND_CONTENT.getMessage());
			}
		}
	}
}
