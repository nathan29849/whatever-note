package dev.whatevernote.be.service.note;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@DisplayName("통합 테스트 : Note 조회")
@Sql("/truncate.sql")
@SpringBootTest
class NoteUpdateTest {

	private static final int NUMBER_OF_NOTE = 10;
	private static final int DEFAULT_RANGE = 1_000;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@BeforeEach
	void init() {
		noteRepository.deleteAll();
		createNotes(NUMBER_OF_NOTE);
	}

	private void createNotes(int numberOfNote) {
		for (int i = 0; i < numberOfNote; i++) {
			noteService.create(new NoteRequestDto(i, "note-" + (i+1)));
		}
	}

	@Nested
	@DisplayName("노트를 수정 할 때")
	class UpdateTest {

		@Nested
		@DisplayName("정상적인 제목 수정 요청이라면")
		class NormalTitleUpdateTest {

			@DisplayName("제목을 변경한 노트를 반환한다.")
			@Test
			void note_title_update(){
			    //given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int updateNoteId = notes.get(seq).getId();
				String updateTitle = "제목 바꾼 노트";
				NoteRequestDto noteRequestDto = new NoteRequestDto(null, updateTitle);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto);

			    //then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getTitle()).isEqualTo(updateTitle);
			}

		}

		@Nested
		@DisplayName("정상적인 순서 수정 요청이라면")
		class NormalSeqUpdateTest {

			@DisplayName("맨 앞으로의 순서 변경 요청일 때, 순서가 첫 번째로 변경된 노트를 반환한다.")
			@Test
			void note_seq_update_to_first(){
				//given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = 0;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE/2);
			}

			@DisplayName("중간으로의 순서 변경 요청일 때, 앞 뒤 노트 순서 정보 합 나누기 2로 순서가 변경된 노트를 반환한다.")
			@Test
			void note_seq_update(){
				//given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = 7;
				int preNoteSeq = DEFAULT_RANGE * updateSeq;
				int nextNoteSeq = DEFAULT_RANGE * (updateSeq+1);
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo((preNoteSeq+nextNoteSeq)/2);
			}

			@DisplayName("맨 뒤로의 순서 변경 요청일 때, 현재 노트 총 개수 곱하기 1000으로 순서가 변경된 노트를 반환한다.")
			@Test
			void note_seq_update_to_last(){
				//given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = NUMBER_OF_NOTE;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo((updateSeq+1) * DEFAULT_RANGE);
			}

			@DisplayName("같은 자리로의 순서 변경 요청일 때, 순서가 변경되지 않은 노트를 반환은다.")
			@Test
			void note_seq_update_to_same_seq(){
				//given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = 3;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo((updateSeq+1) * DEFAULT_RANGE);
			}
		}
	}


}
