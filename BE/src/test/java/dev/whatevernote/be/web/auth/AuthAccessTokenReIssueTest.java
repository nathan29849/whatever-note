package dev.whatevernote.be.web.auth;

import static dev.whatevernote.be.common.ResponseCodeAndMessages.REISSUE_ACCESS_TOKEN_SUCCESS;
import static dev.whatevernote.be.exception.ErrorCodeAndMessages.E400_INVALID_JWT_TOKEN;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.login.config.properties.JwtProperties;
import dev.whatevernote.be.login.service.LoginService;
import dev.whatevernote.be.login.service.provider.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("OAuth Access Token 재발급 테스트")
class AuthAccessTokenReIssueTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private JwtProperties jwtProperties;

	@MockBean
	private LoginService loginService;

	@BeforeEach
	void init(WebApplicationContext wc) {
		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(wc)
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}

	@Nested
	@DisplayName("Access Token을 재발급 받을 때")
	class ReIssueTest {

		@Nested
		@DisplayName("Refresh Token이 유효하다면")
		class NormalTest {

			@Test
			@DisplayName("새로운 Access Token이 발급된다.")
			void reissue_access_token() throws Exception {
				//given
				Long memberId = 1L;
				String refreshToken = jwtProvider.generateRefreshToken(memberId);
				String reIssueAccessToken = jwtProvider.generateAccessToken(memberId);
				BaseResponse<String> baseResponse = new BaseResponse<>(REISSUE_ACCESS_TOKEN_SUCCESS, reIssueAccessToken);

				when(loginService.reIssueAccessToken(refEq(memberId)))
					.thenReturn(reIssueAccessToken);

				//when
				ResultActions resultActions = mockMvc.perform(get("/login/re-issue")
					.header("Authorization", String.format("%s %s", jwtProperties.getTokenType(), refreshToken))
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(reIssueAccessToken)));

				//then
				resultActions.andExpect(status().isOk())
					.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
					.andDo(print());

				verify(loginService).reIssueAccessToken(refEq(memberId));
			}
		}

		@Nested
		@DisplayName("Refresh Token이 유효하지 않다면")
		class AbNormalTest {
			@Test
			@DisplayName("E400_INVALID_JWT_TOKEN 이 발생한다.")
			void invalid_refresh_token() throws Exception {
				//given
				Long memberId = 1L;
				SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(
					StandardCharsets.UTF_8));
				String refreshToken = Jwts.builder()
					.setIssuer(jwtProperties.getIssuer())
					.setSubject(jwtProperties.getRefreshTokenSubject())
					.setAudience(String.valueOf(memberId))
					.setExpiration(new Date())
					.signWith(secretKey)
					.compact();

				String reIssueAccessToken = jwtProvider.generateAccessToken(memberId);
				BaseResponse<String> baseResponse = new BaseResponse<>(E400_INVALID_JWT_TOKEN, null);

				when(loginService.reIssueAccessToken(refEq(memberId)))
					.thenReturn(reIssueAccessToken);

				//when
				ResultActions resultActions = mockMvc.perform(get("/login/re-issue")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.header("Authorization", String.format("%s %s", jwtProperties.getTokenType(), refreshToken))
					.content(objectMapper.writeValueAsString(reIssueAccessToken)));

				//then
				resultActions.andExpect(status().isOk())
					.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
					.andDo(print());
			}
		}

	}

}
