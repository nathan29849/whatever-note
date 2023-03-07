package dev.whatevernote.be.service.card;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.dto.response.CardDetailResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("통합 테스트 : Card & Content 조회")
class CardFindTest extends InitIntegrationTest {

	private static final int NUMBER_OF_CARD = 3;
	private static final int NUMBER_OF_CONTENT = 5;

	private static final int PAGE_NUMBER = 0;
	private static final int PAGE_SIZE = 5;
	private static final String CONTENT_INFO = "contentINFO-";

	@Autowired
	private CardService cardService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private CardRepository cardRepository;

	@Nested
	@DisplayName("카드를 단건 조회할 때")
	class FindOneTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindOneTest {

			@DisplayName("해당 ID의 카드가 조회된다.")
			@Test
			void normal_find_one(){
			    //given
				List<Card> cards = cardRepository.findAllByNoteIdOrderBySeqAsc(FIRST_NOTE_ID);
				int seq = 0;
				long tmpCardId = cards.get(seq).getId();

				//when
				CardDetailResponseDto cardDetailResponseDto = cardService.findById(FIRST_NOTE_ID, tmpCardId, MEMBER_ID);
				List<ContentResponseDto> contents = cardDetailResponseDto.getContents();


				//then
				//card
				assertThat(cardDetailResponseDto.getCardId()).isEqualTo(tmpCardId);
				assertThat(cardDetailResponseDto.getNoteId()).isEqualTo(FIRST_NOTE_ID);
				assertThat(cardDetailResponseDto.getCardTitle()).isEqualTo("cardTitle-" + (seq + 1));

				//contents
				assertThat(contents.get(0).getCardId()).isEqualTo(tmpCardId);
				assertThat(contents.get(0).getInfo()).isEqualTo(CONTENT_INFO+"0");
				assertThat(contents.get(0).getIsImage()).isFalse();
			}
		}

	}

	@Nested
	@DisplayName("카드를 전체 조회할 때")
	class FindAllTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindAllTest {

			@DisplayName("size 요청 횟수 만큼 조회된다.")
			@Test
			void normal_find_all() {
				//given
				PageRequest defaultPageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
				CardResponseDtos cardResponseDtos = cardService.findAll(defaultPageRequest, FIRST_NOTE_ID, MEMBER_ID);

				//when
				List<CardResponseDto> cards = cardResponseDtos.getCards();
				int pageNumber = cardResponseDtos.getPageNumber();
				boolean hasNext = cardResponseDtos.isHasNext();
				long preSeq = 0L;

				//then
				assertThat(cards).hasSize(NUMBER_OF_CARD % PAGE_SIZE);
				assertThat(pageNumber).isEqualTo(PAGE_NUMBER);
				assertThat(hasNext).isFalse();
				for (int i = 0; i < NUMBER_OF_CARD % PAGE_SIZE; i++) {
					CardResponseDto card = cards.get(i);
					assertThat(card.getNoteId()).isEqualTo(FIRST_NOTE_ID);
					assertThat(card.getSeq()).isGreaterThan(preSeq);
					preSeq = card.getSeq();
				}
			}
		}

	}

	@Nested
	@DisplayName("카드의 내용을 단건 조회할 때")
	class FindOneContentTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindOneContentTest {

			@DisplayName("해당 ID를 가진 카드의 내용이 조회된다.")
			@Test
			void normal_find_one_content(){
				//given
				long tmpCardId = 1L;
				long tmpContentId = 1L;

				//when
				ContentResponseDto contentResponseDto = contentService
					.findById(FIRST_NOTE_ID, tmpContentId, MEMBER_ID);

				//then
				assertThat(contentResponseDto.getId()).isEqualTo(tmpContentId);
				assertThat(contentResponseDto.getInfo()).isEqualTo("contentINFO-0");
				assertThat(contentResponseDto.getSeq()).isEqualTo(1000);
				assertThat(contentResponseDto.getIsImage()).isFalse();
				assertThat(contentResponseDto.getCardId()).isEqualTo(tmpCardId);
			}
		}

	}
}
