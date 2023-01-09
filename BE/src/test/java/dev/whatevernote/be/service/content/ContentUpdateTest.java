package dev.whatevernote.be.service.content;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Content 수정")
class ContentUpdateTest extends InitIntegrationTest {

	private static final Long CARD_ID = 1L;
	private static final Long CONTENT_ID = 1L;
	@Autowired
	private ContentService contentService;

	@Nested
	@DisplayName("컨텐트를 수정할 때")
	class UpdateTest {

		@Nested
		@DisplayName("정상적인 Info 수정 요청이라면")
		class NormalInfoUpdateTest {

			@DisplayName("Info를 변경한 컨텐트를 반환한다.")
			@Test
			void content_info_update(){
			    //given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto("수정할 내용", null, null);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo("수정할 내용");
				assertThat(updatedContentResponse.getSeq()).isEqualTo(content.getSeq());
				assertThat(updatedContentResponse.getIsImage()).isEqualTo(content.getIsImage());
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());
			}
		}

		@Nested
		@DisplayName("정상적인 순서 수정 요청이라면")
		class NormalSeqUpdateTest {

			@DisplayName("맨 앞으로의 순서 변경 요청일 때, 순서가 첫 번째로 변경된 컨텐트를 반환한다.")
			@Test
			void content_seq_update_to_first(){
			    //given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto(null, 0L, null);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo(content.getInfo());
				assertThat(updatedContentResponse.getSeq()).isEqualTo(DEFAULT_RANGE/2);
				assertThat(updatedContentResponse.getIsImage()).isEqualTo(content.getIsImage());
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());
			}

			@DisplayName("중간으로의 순서 변경 요청일 때, 앞 뒤 컨텐트 순서 정보 합 나누기 2로 순서가 변경된 컨텐트를 반환한다.")
			@Test
			void content_seq_update(){
				//given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto(null, 3L, null);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo(content.getInfo());
				assertThat(updatedContentResponse.getSeq()).isEqualTo(3500L);
				assertThat(updatedContentResponse.getIsImage()).isEqualTo(content.getIsImage());
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());
			}

			@DisplayName("맨 뒤로의 순서 변경 요청일 때, 현재 컨텐트 총 개수 곱하기 1000으로 순서가 변경된 컨텐트를 반환한다.")
			@Test
			void content_seq_update_to_last(){
				//given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto(null, 100L, null);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo(content.getInfo());
				assertThat(updatedContentResponse.getSeq()).isEqualTo(11 * DEFAULT_RANGE);
				assertThat(updatedContentResponse.getIsImage()).isEqualTo(content.getIsImage());
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());
			}

			@DisplayName("같은 자리로의 순서 변경 요청일 때, 순서가 변경되지 않은 컨텐트를 반환한다.")
			@Test
			void content_seq_update_to_same_seq(){
				//given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto(null, 1L, null);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo(content.getInfo());
				assertThat(updatedContentResponse.getSeq()).isEqualTo(content.getSeq());
				assertThat(updatedContentResponse.getIsImage()).isEqualTo(content.getIsImage());
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());
			}
		}

		@Nested
		@DisplayName("정상적인 isImage 수정이라면")
		class NormalIsImageUpdateTest {

			@DisplayName("isImage가 변경된 컨텐트를 반환한다.")
			@Test
			void content_isImage_update(){
				//given
				ContentResponseDto content = contentService.findById(CONTENT_ID);
				ContentRequestDto contentRequestDto = new ContentRequestDto("update_image.png", null, Boolean.TRUE);

				//when
				ContentResponseDto updatedContentResponse = contentService.update(CARD_ID, CONTENT_ID, contentRequestDto);

				//then
				assertThat(updatedContentResponse.getInfo()).isEqualTo("update_image.png");
				assertThat(updatedContentResponse.getSeq()).isEqualTo(content.getSeq());
				assertThat(content.getIsImage()).isFalse();
				assertThat(updatedContentResponse.getIsImage()).isTrue();
				assertThat(updatedContentResponse.getCardId()).isEqualTo(content.getCardId());

			}
		}
	}



}
