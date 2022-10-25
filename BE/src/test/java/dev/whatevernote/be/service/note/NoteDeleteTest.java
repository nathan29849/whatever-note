package dev.whatevernote.be.service.note;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("통합 테스트 : Note 삭제")
@SpringBootTest
class NoteDeleteTest {

	private static final int NUMBER_OF_NOTE = 5;
	private static final String NOT_FOUNT_NOTE_ID = "존재하지 않는 ID 입니다.";

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
	@DisplayName("노트를 삭제할 때")
	class DeleteTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalDeleteTest {

			@DisplayName("주어진 ID의 노트를 삭제 상태로 바꾼다.")
			@Test
			void soft_delete_note(){
			    //given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 3;
				int deleteNoteId = notes.get(seq).getId();

				//when
				noteService.delete(deleteNoteId);
				List<Note> afterDelete = noteRepository.findAllByOrderBySeq();

				//then
				assertThat(afterDelete).hasSize(NUMBER_OF_NOTE-1);
				assertThatThrownBy(() -> noteService.findById(deleteNoteId))
					.isInstanceOf(Exception.class)
					.hasMessageContaining(NOT_FOUNT_NOTE_ID);
			}
		}
	}



}
