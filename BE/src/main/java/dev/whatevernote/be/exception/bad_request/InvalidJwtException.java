package dev.whatevernote.be.exception.bad_request;

import dev.whatevernote.be.exception.BaseException;
import dev.whatevernote.be.exception.ErrorCodeAndMessages;

public class InvalidJwtException extends BaseException {

	public InvalidJwtException() {
		super(ErrorCodeAndMessages.E400_INVALID_JWT_TOKEN);
	}
}
