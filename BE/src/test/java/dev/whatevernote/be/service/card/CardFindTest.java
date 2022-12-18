package dev.whatevernote.be.service.card;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.CardDetailResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("통합 테스트 : Card 조회")
@Sql("/truncate.sql")
@SpringBootTest
class CardFindTest {

	private static final int NUMBER_OF_CARD = 20;
	private static final int FIRST_NOTE_ID = 1;
	private static final int SECOND_NOTE_ID = 2;
	private static final int PAGE_NUMBER = 0;
	private static final int PAGE_SIZE = 5;
	private static final long FIRST_CARD_ID = 1L;
	private static final long SECOND_CARD_ID = 2L;
	private static final String CONTENT_INFO = "contentInfo-";

	@Autowired
	private CardService cardService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private CardRepository cardRepository;

	@BeforeEach
	void init() {
		cardRepository.deleteAll();
		createNotesAndCardsAndContents(NUMBER_OF_CARD);
	}

	private void createNotesAndCardsAndContents(int numberOfCard){
		for (int i = 0; i < 2; i++) {
			noteService.create(new NoteRequestDto(i, "note-" + (i + 1)));
		}
		for (int i = 0; i < numberOfCard; i++) {
			CardRequestDto cardRequestDto = new CardRequestDto((long) i, "card-" + (i + 1));
			ContentRequestDto contentRequestDto;
			if (i < (numberOfCard / 2)) {
				cardService.create(cardRequestDto, FIRST_NOTE_ID);
				contentRequestDto = new ContentRequestDto(CONTENT_INFO + (i), (long) i, Boolean.FALSE);
			} else {
				cardService.create(cardRequestDto, SECOND_NOTE_ID);
				contentRequestDto = new ContentRequestDto(CONTENT_INFO + (i), (long) i, Boolean.TRUE);
			}

			contentService.create(contentRequestDto, FIRST_CARD_ID);
			if (i > 0) {
				contentService.create(contentRequestDto, SECOND_CARD_ID);
			}
		}
	}

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
				List<Card> cards = cardRepository.findAllByOrderBySeq();
				int seq = 0;
				long tmpCardId = cards.get(seq).getId();

				//when
				CardDetailResponseDto cardDetailResponseDto = cardService.findById(FIRST_NOTE_ID, tmpCardId);
				List<ContentResponseDto> contents = cardDetailResponseDto.getContents();

				//then
				//card
				assertThat(cardDetailResponseDto.getCardId()).isEqualTo(tmpCardId);
				assertThat(cardDetailResponseDto.getNoteId()).isEqualTo(FIRST_NOTE_ID);
				assertThat(cardDetailResponseDto.getCardTitle()).isEqualTo("card-" + (seq + 1));

				//contents
				assertThat(cardDetailResponseDto.getContents()).hasSize(NUMBER_OF_CARD);
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
				CardResponseDtos cardResponseDtos = cardService.findAll(defaultPageRequest, FIRST_NOTE_ID);

				//when
				List<CardResponseDto> cards = cardResponseDtos.getCards();
				int pageNumber = cardResponseDtos.getPageNumber();
				boolean hasNext = cardResponseDtos.isHasNext();
				long preSeq = 0L;

				//then
				assertThat(cards).hasSize(PAGE_SIZE);
				assertThat(pageNumber).isEqualTo(PAGE_NUMBER);
				assertThat(hasNext).isTrue();
				for (int i = 0; i < PAGE_SIZE; i++) {
					CardResponseDto card = cards.get(i);
					assertThat(card.getNoteId()).isEqualTo(FIRST_NOTE_ID);
					assertThat(card.getSeq()).isGreaterThan(preSeq);
					preSeq = card.getSeq();
				}
			}
		}

	}
}
