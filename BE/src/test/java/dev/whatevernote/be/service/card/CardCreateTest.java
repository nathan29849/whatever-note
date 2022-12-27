package dev.whatevernote.be.service.card;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Card 생성")
public class CardCreateTest extends InitIntegrationTest {

	private static final int DEFAULT_RANGE = 1_000;
	private static final int TEMP_NOTE_ID = 1;
	private static final int NUMBER_OF_NOTE = 3;

	@Autowired
	private CardService cardService;

	@Autowired
	private NoteService noteService;

	@Nested
	@DisplayName("카드를 생성할 때")
	class CreateTest {

		@Nested
		@DisplayName("정상적인 생성 요청이라면")
		class CardNormalCreateTest {

			@Test
			@DisplayName("기존에 카드가 없을 때, default(=1000)를 번호로 가지는 카드가 생성된다. (카드 생성 요청 번호가 0인 경우)")
			void not_existing_card_with_request_seq_is_zero() {
				//given
				noteService.create(
					new NoteRequestDto(null, "새로 생성한 노트")
				);
				NoteResponseDto note = noteService.findById(NUMBER_OF_NOTE + 1);
				CardRequestDto cardRequestDto = new CardRequestDto(0L, "나만의 카드");

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto, note.getId());

				//then
				Assertions.assertThat(cardResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}

			@Test
			@DisplayName("기존에 카드가 없을 때, default(=1000)를 번호로 가지는 카드가 생성된다.(카드 생성 요청 번호가 0이 아닌 경우)")
			void not_existing_card_with_request_seq_is_not_zero() {
				//given
				noteService.create(
					new NoteRequestDto(null, "새로 생성한 노트")
				);
				NoteResponseDto note = noteService.findById(NUMBER_OF_NOTE + 1);
				CardRequestDto cardRequestDto = new CardRequestDto(10L, "나만의 카드");

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto, note.getId());

				//then
				assertThat(cardResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}

			@Test
			@DisplayName("기존에 카드 개수가 요청된 노트 순서보다 클 때, 맨 마지막 순서로 카드가 생성된다.")
			void existing_card_with_over_request_seq() {
				//given
				noteService.create(
					new NoteRequestDto(null, "새로 생성한 노트")
				);
				NoteResponseDto note = noteService.findById(NUMBER_OF_NOTE + 1);
				cardService.create(new CardRequestDto(0L, "나만의 카드1"), note.getId());
				cardService.create(new CardRequestDto(1L, "나만의 카드2"), note.getId());
				cardService.create(new CardRequestDto(2L, "나만의 카드3"), note.getId());
				CardRequestDto cardRequestDto = new CardRequestDto(10L, "나만의 카드");
				int numberOfNotes = 4;

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto, note.getId());

				//then
				assertThat(cardResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE*numberOfNotes);

			}

		}


		@Nested
		@DisplayName("카드 생성 위치가 없는 카드 생성 요청이라면")
		class NoteSeqOmissionCreateTest{

			@Test
			@DisplayName("기존에 카드가 하나라도 존재할 때, 가장 빠른 번호의 절반으로 카드 번호를 할당하여 생성한다.")
			void seq_null_and_existing_card(){
				//given
				CardRequestDto cardRequestDto = new CardRequestDto(null, "나만의 단어장");

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto, TEMP_NOTE_ID);

				//then
				assertThat(cardResponseDto.getSeq()).isEqualTo(500L);
				assertThat(cardResponseDto.getTitle()).isEqualTo("나만의 단어장");
			}

			@Test
			@DisplayName("기존에 카드가 하나도 없을 때, default(=1000)로 카드 번호를 할당하여 생성한다.")
			void seq_null_and_not_existing_card(){
				//given
				noteService.create(
					new NoteRequestDto(null, "새로 생성한 노트")
				);
				NoteResponseDto note = noteService.findById(NUMBER_OF_NOTE + 1);
				CardRequestDto cardRequestDto = new CardRequestDto(null, "나만의 단어장");

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto,  note.getId());

				//then
				assertThat(cardResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}


		}

		@Nested
		@DisplayName("카드 제목이 없는 카드 생성 요청이라면")
		class NoteTitleOmissionCreateTest{

			@Test
			@DisplayName("빈 문자열을 제목으로 갖는 카드를 생성한다.")
			void null_title(){
				//given
				CardRequestDto cardRequestDto = new CardRequestDto(null, null);

				//when
				CardResponseDto cardResponseDto = cardService.create(cardRequestDto, TEMP_NOTE_ID);

				//then
				assertThat(cardResponseDto.getTitle()).isNull();
			}


		}
	}
}


