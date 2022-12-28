package dev.whatevernote.be.service.note;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.InitIntegrationTest;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@DisplayName("통합 테스트 : Note 조회")
class NoteFindTest extends InitIntegrationTest {

	private static final int PAGE_NUMBER = 0;
	private static final int PAGE_SIZE = 10;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@Nested
	@DisplayName("노트를 단건 조회할 때")
	class FindOneTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindOneTest {

			@DisplayName("해당 ID의 노트가 조회된다.")
			@Test
			void normal_find_one() {
				//given
				List<Note> notes = noteRepository.findAllByOrderBySeq();
				int seq = 2;
				int tmpNoteId = notes.get(2).getId();


				//when
				NoteResponseDto noteResponseDto = noteService.findById(tmpNoteId);

				//then
				assertThat(noteResponseDto.getId()).isEqualTo(tmpNoteId);
				assertThat(noteResponseDto.getTitle()).isEqualTo("noteTitle-" + (seq+1));
			}
		}
	}

	@Nested
	@DisplayName("노트를 전체 조회할 때")
	class FindAllTest {

		@Nested
		@DisplayName("정상적인 요청이라면")
		class NormalFindAllTest {


			@DisplayName("size 요청 횟수 만큼 조회된다.")
			@Test
			void normal_find_all() {
				//given
				PageRequest defaultPageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
				NoteResponseDtos noteRequestDtos = noteService.findAll(defaultPageRequest);

				//when
				List<NoteResponseDto> notes = noteRequestDtos.getNotes();
				int pageNumber = noteRequestDtos.getPageNumber();
				boolean hasNext = noteRequestDtos.isHasNext();
				int preSeq = 0;

				//then
				assertThat(notes.size()).isLessThan(PAGE_SIZE);
				assertThat(pageNumber).isEqualTo(PAGE_NUMBER);
				assertThat(hasNext).isFalse();
				for (NoteResponseDto note : notes) {
					assertThat(note.getSeq()).isGreaterThan(preSeq);
					preSeq = note.getSeq();
				}

			}
		}
	}
}

