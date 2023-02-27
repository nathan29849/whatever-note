package dev.whatevernote.be.login.web;

import static dev.whatevernote.be.common.ResponseCodeAndMessages.OAUTH_LOGIN_SUCCESS;
import static dev.whatevernote.be.common.ResponseCodeAndMessages.REISSUE_ACCESS_TOKEN_SUCCESS;

import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.login.Login;
import dev.whatevernote.be.login.service.LoginService;
import dev.whatevernote.be.login.service.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@GetMapping("/kakao")
	public BaseResponse<LoginResponse> login(@RequestParam final String code) {
		LoginResponse response = loginService.login(code);
		return new BaseResponse<>(OAUTH_LOGIN_SUCCESS, response);
	}

	@GetMapping("/re-issue")
	public BaseResponse<String> reIssueAccessToken(@Login final Long memberId) {
		String response = loginService.reIssueAccessToken(memberId);
		return new BaseResponse<>(REISSUE_ACCESS_TOKEN_SUCCESS, response);
	}


}
