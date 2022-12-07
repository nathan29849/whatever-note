package dev.whatevernote.be.service.card;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
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

	@Autowired
	private CardService cardService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private CardRepository cardRepository;

	@BeforeEach
	void init() {
		cardRepository.deleteAll();
		createNotesAndCards(NUMBER_OF_CARD);
	}

	private void createNotesAndCards(int numberOfCard){
		for (int i = 0; i < 2; i++) {
			noteService.create(new NoteRequestDto(i, "note-" + (i + 1)));
		}
		for (int i = 0; i < numberOfCard; i++) {
			CardRequestDto cardRequestDto = new CardRequestDto((long) i, "card-" + (i + 1));
			if (i < (numberOfCard / 2)) {
				cardService.create(cardRequestDto, FIRST_NOTE_ID);
			} else {
				cardService.create(cardRequestDto, SECOND_NOTE_ID);
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
