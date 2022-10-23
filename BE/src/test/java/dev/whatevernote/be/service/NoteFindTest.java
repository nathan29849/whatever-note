package dev.whatevernote.be.service;

import static org.assertj.core.api.Assertions.assertThat;

import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@DisplayName("통합 테스트 : Note 조회")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class NoteFindTest {

	private static final int NUMBER_OF_NOTE = 100;
	private static final int PAGE_NUMBER = 1;
	private static final int PAGE_SIZE = 10;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteRepository noteRepository;

	@BeforeAll
	void init() {
		noteRepository.deleteAll();
		createNotes(NUMBER_OF_NOTE);
	}

	private void createNotes(int numberOfNote) {
		for (int i = 0; i < numberOfNote; i++) {
			noteService.create(new NoteRequestDto(i, "note-" + i));
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
			void normal_find_all_with_no_page_size() {
				//given
				PageRequest defaultPageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
				NoteResponseDtos noteRequestDtos = noteService.findAll(defaultPageRequest);

				//when
				List<NoteResponseDto> notes = noteRequestDtos.getNotes();
				int pageNumber = noteRequestDtos.getPageNumber();
				boolean hasNext = noteRequestDtos.isHasNext();
				int preSeq = 0;

				//then
				assertThat(notes).hasSize(PAGE_SIZE);
				assertThat(pageNumber).isEqualTo(PAGE_NUMBER);
				assertThat(hasNext).isTrue();
				for (NoteResponseDto note : notes) {
					assertThat(note.getSeq()).isGreaterThan(preSeq);
					preSeq = note.getSeq();
				}

			}
		}
	}
}

