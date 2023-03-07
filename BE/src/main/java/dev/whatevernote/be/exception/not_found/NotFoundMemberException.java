package dev.whatevernote.be.exception.not_found;

import dev.whatevernote.be.exception.BaseException;
import dev.whatevernote.be.exception.ErrorCodeAndMessages;

public class NotFoundMemberException extends BaseException {

	public NotFoundMemberException() {
		super(ErrorCodeAndMessages.E404_NOT_FOUND_MEMBER);
	}
}
