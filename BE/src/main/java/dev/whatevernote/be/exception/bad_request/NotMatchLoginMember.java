package dev.whatevernote.be.exception.bad_request;

import dev.whatevernote.be.exception.BaseException;
import dev.whatevernote.be.exception.ErrorCodeAndMessages;

public class NotMatchLoginMember extends BaseException {

	public NotMatchLoginMember() {
		super(ErrorCodeAndMessages.E400_NOT_MATCH_LOGIN_MEMBER);
	}
}
