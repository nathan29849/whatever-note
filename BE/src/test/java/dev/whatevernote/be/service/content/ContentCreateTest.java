package dev.whatevernote.be.service.content;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Content 생성")
class ContentCreateTest extends InitIntegrationTest {

	@Autowired
	private CardService cardService;
	@Autowired
	private ContentService contentService;

	@Nested
	@DisplayName("컨텐트를 생성할 때")
	class CreateTest {

		@Nested
		@DisplayName("정상적인 생성 요청이라면")
		class ContentNormalCreateTest {

			@DisplayName("기존에 컨텐트가 없을 때, default(=1000)를 번호로 가지는 컨텐트가 생성된다. (컨텐트 생성 요청 번호가 0인 경우)")
			@Test
			void not_existing_content_with_request_seq_is_zero(){
			    //given
				CardResponseDto cardResponse = cardService.create(
					new CardRequestDto(null, "컨텐트 생성 테스트를 위한 카드"), FIRST_NOTE_ID, MEMBER_ID);
				Long newCardId = cardResponse.getId();

				//when
				ContentResponseDto contentResponse = contentService.create(
					new ContentRequestDto("새로운 컨텐트", 0L, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				//then
				assertThat(contentResponse.getCardId()).isEqualTo(newCardId);
				assertThat(contentResponse.getSeq()).isEqualTo(DEFAULT_RANGE);
				assertThat(contentResponse.getInfo()).isEqualTo("새로운 컨텐트");
				assertThat(contentResponse.getIsImage()).isFalse();
			}

			@DisplayName("기존에 컨텐트가 없을 때, default(=1000)를 번호로 가지는 컨텐트가 생성된다. (컨텐트 생성 요청 번호가 0이 아닌 경우)")
			@Test
			void not_existing_content_with_request_seq_is_not_zero(){
				//given
				CardResponseDto cardResponse = cardService.create(
					new CardRequestDto(null, "컨텐트 생성 테스트를 위한 카드"), FIRST_NOTE_ID, MEMBER_ID);
				Long newCardId = cardResponse.getId();

				//when
				ContentResponseDto contentResponse = contentService.create(
					new ContentRequestDto("새로운 컨텐트", 10L, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				//then
				assertThat(contentResponse.getCardId()).isEqualTo(newCardId);
				assertThat(contentResponse.getSeq()).isEqualTo(DEFAULT_RANGE);
				assertThat(contentResponse.getInfo()).isEqualTo("새로운 컨텐트");
				assertThat(contentResponse.getIsImage()).isFalse();
			}

			@DisplayName("기존에 컨텐트 개수가 요청된 컨텐트 순서보다 클 때, 맨 마지막 순서로 컨텐트가 생성된다.")
			@Test
			void existing_content_with_over_request_seq(){
				//given
				CardResponseDto cardResponse = cardService.create(
					new CardRequestDto(null, "컨텐트 생성 테스트를 위한 카드"), FIRST_NOTE_ID, MEMBER_ID);
				Long newCardId = cardResponse.getId();

				//when
				ContentResponseDto contentResponse = contentService.create(
					new ContentRequestDto("새로운 컨텐트", 0L, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				ContentResponseDto contentResponse2 = contentService.create(
					new ContentRequestDto("새로운 컨텐트2", 10L, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				//then
				assertThat(contentResponse2.getCardId()).isEqualTo(newCardId);
				assertThat(contentResponse2.getSeq()).isEqualTo(DEFAULT_RANGE * 2);
				assertThat(contentResponse2.getInfo()).isEqualTo("새로운 컨텐트2");
				assertThat(contentResponse2.getIsImage()).isFalse();
			}
		}

		@Nested
		@DisplayName("컨텐트 생성 위치가 없는 생성 요청이라면")
		class ContentSeqOmissionCreateTest {

			@DisplayName("기존에 컨텐트가 하나라도 존재할 때, 가장 빠른 번호의 절반으로 컨텐트 번호를 할당하여 생성한다.")
			@Test
			void seq_null_and_existing_content(){
				//given
				CardResponseDto cardResponse = cardService.create(
					new CardRequestDto(null, "컨텐트 생성 테스트를 위한 카드"), FIRST_NOTE_ID, MEMBER_ID);
				Long newCardId = cardResponse.getId();

				//when
				ContentResponseDto contentResponse = contentService.create(
					new ContentRequestDto("새로운 컨텐트", 0L, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				ContentResponseDto contentResponse2 = contentService.create(
					new ContentRequestDto("새로운 컨텐트2", null, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				//then
				assertThat(contentResponse2.getCardId()).isEqualTo(newCardId);
				assertThat(contentResponse2.getSeq()).isEqualTo(DEFAULT_RANGE/2);
				assertThat(contentResponse2.getInfo()).isEqualTo("새로운 컨텐트2");
				assertThat(contentResponse2.getIsImage()).isFalse();
			}

			@DisplayName("기존에 컨텐트가 하나라도 존재할 때, default(=1000)로 컨텐트 번호를 할당하여 생성한다.")
			@Test
			void seq_null_and_not_existing_content(){
				//given
				CardResponseDto cardResponse = cardService.create(
					new CardRequestDto(null, "컨텐트 생성 테스트를 위한 카드"), FIRST_NOTE_ID, MEMBER_ID);
				Long newCardId = cardResponse.getId();

				//when
				ContentResponseDto contentResponse = contentService.create(
					new ContentRequestDto("새로운 컨텐트", null, Boolean.FALSE),
					FIRST_NOTE_ID, newCardId,
					MEMBER_ID
				);

				//then
				assertThat(contentResponse.getCardId()).isEqualTo(newCardId);
				assertThat(contentResponse.getSeq()).isEqualTo(DEFAULT_RANGE);
				assertThat(contentResponse.getInfo()).isEqualTo("새로운 컨텐트");
				assertThat(contentResponse.getIsImage()).isFalse();
			}
		}
	}

}
