package dev.whatevernote.be.exception.note;

import dev.whatevernote.be.exception.BaseException;
import dev.whatevernote.be.exception.ErrorCodeAndMessages;

public class NoteIdNotFoundException extends BaseException {
	public NoteIdNotFoundException() {
		super(ErrorCodeAndMessages.E404_NOT_FOUND_NOTE);
	}
}
