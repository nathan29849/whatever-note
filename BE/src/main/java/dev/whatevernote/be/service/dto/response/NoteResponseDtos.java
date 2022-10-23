package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Note;
import java.util.List;
import org.springframework.data.domain.Slice;

public class NoteResponseDtos {

	private List<NoteResponseDto> notes;
	private boolean hasNext;
	private int pageNumber;

	public NoteResponseDtos(List<NoteResponseDto> notes, boolean hasNext, int pageNumber) {
		this.notes = notes;
		this.hasNext = hasNext;
		this.pageNumber = pageNumber;
	}

	public List<NoteResponseDto> getNotes() {
		return notes;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public static NoteResponseDtos from(Slice<Note> notes) {
		return new NoteResponseDtos(
			notes.map(NoteResponseDto::from).getContent(),
			notes.hasNext(),
			notes.getNumber()
		);
	}
}
