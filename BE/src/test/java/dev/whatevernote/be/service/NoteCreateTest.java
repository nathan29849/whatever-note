package dev.whatevernote.be.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("통합 테스트 : Note 생성")
@SpringBootTest
class NoteCreateTest {

	private static final int DEFAULT_RANGE = 1_000;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@BeforeEach
	void init() {
		noteRepository.deleteAll();
	}

	@Nested
	@DisplayName("노트를 생성할 때")
	class CreateTest {

		@Nested
		@DisplayName("정상적인 생성 요청이라면")
		class NoteNormalCreateTest{

			@Test
			@DisplayName("기존에 노트가 없을 때, default(=1000)를 번호로 가지는 노트가 생성된다.(노트 생성 요청 번호가 0인 경우)")
			void not_existing_note_with_request_seq_is_zero() {
				//given
				NoteRequestDto noteRequestDto = new NoteRequestDto(0, "나만의 단어장");

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}

			@Test
			@DisplayName("기존에 노트가 없을 때, default(=1000)를 번호로 가지는 노트가 생성된다.(노트 생성 요청 번호가 0이 아닌 경우)")
			void not_existing_note_with_request_seq_is_not_zero() {
				//given
				NoteRequestDto noteRequestDto = new NoteRequestDto(10, "나만의 단어장");

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}

			@Test
			@DisplayName("기존에 노트가 하나라도 존재하면, 요청된 노트 순서의 다음 순서로 노트가 생성된다.")
			void existing_note() {
				//given
				noteService.create(new NoteRequestDto(0, "나만의 단어장1"));
				noteService.create(new NoteRequestDto(1, "나만의 단어장2"));
				noteService.create(new NoteRequestDto(2, "나만의 단어장3"));
				NoteRequestDto noteRequestDto = new NoteRequestDto(2, "나만의 단어장");

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(2500);

			}

			@Test
			@DisplayName("기존에 노트 개수가 요청된 노트 순서보다 클 때, 맨 마지막 순서로 노트가 생성된다.")
			void existing_note_with_over_request_seq() {
				//given
				noteService.create(new NoteRequestDto(0, "나만의 단어장1"));
				noteService.create(new NoteRequestDto(1, "나만의 단어장2"));
				noteService.create(new NoteRequestDto(2, "나만의 단어장3"));
				NoteRequestDto noteRequestDto = new NoteRequestDto(10, "나만의 단어장");
				int numberOfNotes = 4;

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE*numberOfNotes);

			}

		}


		@Nested
		@DisplayName("노트 생성 위치가 없는 노트 생성 요청이라면")
		class NoteSeqOmissionCreateTest{

			@Test
			@DisplayName("기존에 노트가 하나라도 존재할 때, 가장 빠른 번호의 절반으로 노트 번호가 할당하여 생성한다.")
			void seq_null_and_existing_note(){
				//given
				noteService.create(new NoteRequestDto(0, "나만의 단어장1"));
				noteService.create(new NoteRequestDto(1, "나만의 단어장2"));
				noteService.create(new NoteRequestDto(2, "나만의 단어장3"));
				NoteRequestDto noteRequestDto = new NoteRequestDto(null, "나만의 단어장");

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(500);
			}

			@Test
			@DisplayName("기존에 노트가 하나도 없을 때, default(=1000)로 노트 번호를 할당하여 생성한다.")
			void seq_null_and_not_existing_note(){
				//given
				NoteRequestDto noteRequestDto = new NoteRequestDto(null, "나만의 단어장");

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE);
			}


		}

		@Nested
		@DisplayName("노트 제목이 없는 노트 생성 요청이라면")
		class NoteTitleOmissionCreateTest{

			@Test
			@DisplayName("빈 문자열을 제목으로 갖는 노트를 생성한다.")
			void null_title(){
				//given
				NoteRequestDto noteRequestDto = new NoteRequestDto(null, null);

				//when
				NoteResponseDto noteResponseDto = noteService.create(noteRequestDto);

				//then
				assertThat(noteResponseDto.getTitle()).isNull();
			}


		}

	}



}
