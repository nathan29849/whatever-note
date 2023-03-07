package dev.whatevernote.be.service.note;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 테스트 : Note 수정")
class NoteUpdateTest extends InitIntegrationTest {

	private static final int NUMBER_OF_NOTE = 3;
	private static final int DEFAULT_RANGE = 1_000;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@Nested
	@DisplayName("노트를 수정할 때")
	class UpdateTest {

		@Nested
		@DisplayName("정상적인 제목 수정 요청이라면")
		class NormalTitleUpdateTest {

			@DisplayName("제목을 변경한 노트를 반환한다.")
			@Test
			void note_title_update(){
			    //given
				List<Note> notes = noteRepository.findAllByMemberIdOrderBySeq(MEMBER_ID);
				int seq = 2;
				int updateNoteId = notes.get(seq).getId();
				String updateTitle = "제목 바꾼 노트";
				NoteRequestDto noteRequestDto = new NoteRequestDto(null, updateTitle);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto, MEMBER_ID);

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
				List<Note> notes = noteRepository.findAllByMemberIdOrderBySeq(MEMBER_ID);
				int updateNoteId = 1;
				int updateSeq = 0;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto, MEMBER_ID);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo(DEFAULT_RANGE/2);
			}

			@DisplayName("중간으로의 순서 변경 요청일 때, 앞 뒤 노트 순서 정보 합 나누기 2로 순서가 변경된 노트를 반환한다.")
			@Test
			void note_seq_update(){
				//given
				List<Note> notes = noteRepository.findAllByMemberIdOrderBySeq(MEMBER_ID);
				int seq = 2;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = 1;
				int preNoteSeq = DEFAULT_RANGE * updateSeq;
				int nextNoteSeq = DEFAULT_RANGE * (updateSeq+1);
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto, MEMBER_ID);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo((preNoteSeq+nextNoteSeq)/2);
			}

			@DisplayName("맨 뒤로의 순서 변경 요청일 때, 현재 노트 총 개수 곱하기 1000으로 순서가 변경된 노트를 반환한다.")
			@Test
			void note_seq_update_to_last(){
				//given
				List<Note> notes = noteRepository.findAllByMemberIdOrderBySeq(MEMBER_ID);
				int seq = 1;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = NUMBER_OF_NOTE;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto, MEMBER_ID);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo((updateSeq+1) * DEFAULT_RANGE);
			}

			@DisplayName("같은 자리로의 순서 변경 요청일 때, 순서가 변경되지 않은 노트를 반환은다.")
			@Test
			void note_seq_update_to_same_seq(){
				//given
				List<Note> notes = noteRepository.findAllByMemberIdOrderBySeq(MEMBER_ID);
				int seq = 1;
				int updateNoteId = notes.get(seq).getId();
				int updateSeq = 2;
				NoteRequestDto noteRequestDto = new NoteRequestDto(updateSeq, null);

				//when
				NoteResponseDto noteResponseDto = noteService.update(updateNoteId, noteRequestDto, MEMBER_ID);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(updateNoteId);
				assertThat(noteResponseDto.getSeq()).isEqualTo(updateSeq * DEFAULT_RANGE);
			}
		}
	}


}
