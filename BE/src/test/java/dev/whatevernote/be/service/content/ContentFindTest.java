package dev.whatevernote.be.service.content;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDtos;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("통합 테스트 : Content 조회")
class ContentFindTest extends InitIntegrationTest {

	private static final int PAGE_NUMBER = 0;
	private static final int PAGE_SIZE = 5;

	private static final Long CARD_ID = 1L;

	@Autowired
	private ContentService contentService;

	@Autowired
	private ContentRepository contentRepository;

	@Nested
	@DisplayName("컨텐트를 전체 조회할 때")
	class FindAllTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindAllTest {

			@DisplayName("해당 카드가 가진 모든 컨텐트들이 조회된다.")
			@Test
			void normal_find_all(){
			    //given
				// [CARD ID = 1] CONTENTS 개수: 10개
				PageRequest defaultPageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
				ContentResponseDtos contentResponseDtos = contentService.findAll(defaultPageRequest,
					FIRST_NOTE_ID, CARD_ID,
					MEMBER_ID);

				//when
				long preSeq = 0L;
				List<ContentResponseDto> contents = contentResponseDtos.getContents();

				//then
				assertThat(contents).hasSizeLessThanOrEqualTo(PAGE_SIZE);
				assertThat(contentResponseDtos.getPageNumber()).isEqualTo(PAGE_NUMBER);
				assertThat(contentResponseDtos.isHasNext()).isTrue();
				for (ContentResponseDto content : contents) {
					assertThat(content.getSeq()).isGreaterThan(preSeq);
					preSeq = content.getSeq();
				}

			}
		}
	}
}
