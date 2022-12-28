package dev.whatevernote.be.service.dto.request;

public class NoteRequestDto {

	private Integer seq;
	private String title;

	private NoteRequestDto() {
	}

	public NoteRequestDto(Integer seq, String title) {
		this.seq = seq;
		this.title = title;
	}

	public Integer getSeq() {
		return seq;
	}

	public String getTitle() {
		return title;
	}

}
