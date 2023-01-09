package dev.whatevernote.be.service.dto.response;

import dev.whatevernote.be.service.domain.Content;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Slice;

public class ContentResponseDtos {

	private final List<ContentResponseDto> contents;
	private final boolean hasNext;
	private final int pageNumber;

	public ContentResponseDtos(List<ContentResponseDto> contents, boolean hasNext, int pageNumber) {
		this.contents = contents;
		this.hasNext = hasNext;
		this.pageNumber = pageNumber;
	}

	public List<ContentResponseDto> getContents() {
		return contents;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public static ContentResponseDtos from(Slice<Content> contents) {
		return new ContentResponseDtos(
			contents.stream()
				.map(ContentResponseDto::from)
				.collect(Collectors.toList()),
			contents.hasNext(),
			contents.getNumber()
		);
	}
}
