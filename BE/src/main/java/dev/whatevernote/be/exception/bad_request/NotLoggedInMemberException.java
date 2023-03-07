package dev.whatevernote.be.exception.bad_request;

import dev.whatevernote.be.exception.BaseException;
import dev.whatevernote.be.exception.ErrorCodeAndMessages;

public class NotLoggedInMemberException extends BaseException {

	public NotLoggedInMemberException() {
		super(ErrorCodeAndMessages.E400_NOT_LOGGED_IN_LOGIN_MEMBER);
	}
}
