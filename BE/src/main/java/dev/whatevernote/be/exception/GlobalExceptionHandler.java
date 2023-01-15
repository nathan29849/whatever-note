package dev.whatevernote.be.exception;

import dev.whatevernote.be.common.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public BaseResponse<Void> exceptionHandle(BaseException e){
		return new BaseResponse<>(e.getCode(), e.getMessage(), null);
	}
}
