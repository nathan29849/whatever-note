package dev.whatevernote.be.service.card;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.response.CardDetailResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Card 수정")
class CardUpdateTest extends InitIntegrationTest {

	private static final int NOTE_ID_1 = 1;
	private static final long CARD_ID_1 = 1;
	private static final long CARD_ID_3 = 3;

	@Autowired
	private CardService cardService;

	@Autowired
	private CardRepository cardRepository;

	@Nested
	@DisplayName("카드를 수정할 때")
	class UpdateTest {

		@Nested
		@DisplayName("정상적인 제목 수정 요청이라면")
		class NormalTitleUpdateTest {

			@DisplayName("제목을 변경한 카드를 반환한다.")
			@Test
			void card_title_update() {
				//given
				CardDetailResponseDto card = cardService.findById(NOTE_ID_1, CARD_ID_1, MEMBER_ID);
				CardRequestDto cardRequestDto = new CardRequestDto(null, "제목 바꾼 카드");

				//when
				CardResponseDto updatedCardResponse = cardService.update(NOTE_ID_1, CARD_ID_1, cardRequestDto,
					MEMBER_ID);

				//then
				assertThat(updatedCardResponse.getTitle()).isEqualTo("제목 바꾼 카드");
				assertThat(updatedCardResponse.getSeq()).isEqualTo(card.getCardSeq());
				assertThat(updatedCardResponse.getId()).isEqualTo(CARD_ID_1);
				assertThat(updatedCardResponse.getNoteId()).isEqualTo(NOTE_ID_1);
			}
		}

		@Nested
		@DisplayName("정상적인 순서 수정 요청이라면")
		class NormalSeqUpdateTest {

			@DisplayName("맨 앞으로의 순서 변경 요청일 때, 순서가 첫 번째로 변경된 카드를 반환한다.")
			@Test
			void card_seq_update_to_first() {
				//given
				CardDetailResponseDto card = cardService.findById(NOTE_ID_1, CARD_ID_3, MEMBER_ID);
				CardRequestDto cardRequestDto = new CardRequestDto(0L, null);
				
				//when
				CardResponseDto updatedCardResponse = cardService.update(NOTE_ID_1, CARD_ID_3, cardRequestDto,
					MEMBER_ID);

				//then
				assertThat(updatedCardResponse.getTitle()).isEqualTo(card.getCardTitle());
				assertThat(updatedCardResponse.getSeq()).isEqualTo(DEFAULT_RANGE/2);
				assertThat(updatedCardResponse.getId()).isEqualTo(CARD_ID_3);
				assertThat(updatedCardResponse.getNoteId()).isEqualTo(NOTE_ID_1);

			}
			@DisplayName("중간으로의 순서 변경 요청일 때, 앞 뒤 카드 순서 정보 합 나누기 2로 순서가 변경된 카드를 반환한다.")
			@Test
			void card_seq_update(){
				//given
				CardDetailResponseDto card = cardService.findById(NOTE_ID_1, CARD_ID_3, MEMBER_ID);
				CardRequestDto cardRequestDto = new CardRequestDto(1L, null);

				//when
				CardResponseDto updatedCardResponse = cardService.update(NOTE_ID_1, CARD_ID_3, cardRequestDto,
					MEMBER_ID);

				//then
				assertThat(updatedCardResponse.getTitle()).isEqualTo(card.getCardTitle());
				assertThat(updatedCardResponse.getSeq()).isEqualTo((DEFAULT_RANGE + 2*DEFAULT_RANGE)/2);
				assertThat(updatedCardResponse.getId()).isEqualTo(CARD_ID_3);
				assertThat(updatedCardResponse.getNoteId()).isEqualTo(NOTE_ID_1);
			}

			@DisplayName("맨 뒤로의 순서 변경 요청일 때, 현재 카드 총 개수 곱하기 1000으로 순서가 변경된 카드를 반환한다.")
			@Test
			void card_seq_update_to_last(){
				//given
				CardDetailResponseDto card = cardService.findById(NOTE_ID_1, CARD_ID_1, MEMBER_ID);
				CardRequestDto cardRequestDto = new CardRequestDto(100L, null);

				//when
				CardResponseDto updatedCardResponse = cardService.update(NOTE_ID_1, CARD_ID_1, cardRequestDto,
					MEMBER_ID);

				//then
				assertThat(updatedCardResponse.getTitle()).isEqualTo(card.getCardTitle());
				assertThat(updatedCardResponse.getSeq()).isEqualTo(DEFAULT_RANGE * 4);
				assertThat(updatedCardResponse.getId()).isEqualTo(CARD_ID_1);
				assertThat(updatedCardResponse.getNoteId()).isEqualTo(NOTE_ID_1);
			}

			@DisplayName("같은 자리로의 순서 변경 요청일 때, 순서가 변경되지 않은 카드를 반환한다.")
			@Test
			void card_seq_update_to_same_seq(){
				//given
				CardDetailResponseDto card = cardService.findById(NOTE_ID_1, CARD_ID_1, MEMBER_ID);
				CardRequestDto cardRequestDto = new CardRequestDto(1L, null);

				//when
				CardResponseDto updatedCardResponse = cardService.update(NOTE_ID_1, CARD_ID_1, cardRequestDto,
					MEMBER_ID);

				//then
				assertThat(updatedCardResponse.getTitle()).isEqualTo(card.getCardTitle());
				assertThat(updatedCardResponse.getSeq()).isEqualTo(card.getCardSeq());
				assertThat(updatedCardResponse.getId()).isEqualTo(CARD_ID_1);
				assertThat(updatedCardResponse.getNoteId()).isEqualTo(NOTE_ID_1);
			}

		}
	}
}
