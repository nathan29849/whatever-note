package dev.whatevernote.be.web.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import dev.whatevernote.be.tool.AuthMocks;
import dev.whatevernote.be.login.repository.MemberRepository;
import dev.whatevernote.be.login.service.LoginService;
import dev.whatevernote.be.login.service.domain.Member;
import dev.whatevernote.be.login.service.dto.response.LoginResponse;
import dev.whatevernote.be.login.service.provider.JwtProvider;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
	"oauth.kakao.token-url=http://localhost:${wiremock.server.port}/oauth/token",
	"oauth.kakao.resource-url=http://localhost:${wiremock.server.port}/v2/user/me"
})
@DisplayName("Auth Login 통합 테스트")
@SpringBootTest
@ActiveProfiles("test")
class AuthLoginIntegrationTest {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void init() throws IOException {
		memberRepository.deleteAllInBatch();
		AuthMocks.setUpResponses();
	}

	@Test
	@DisplayName("신규 로그인을 시도했을 때, 로그인 성공 및 멤버 생성 성공")
	void auth_login_success_with_new_member_create(){
	    //given
		WireMock.setScenarioState("Kakao Login Success", "Started");

	    //when
		LoginResponse response = loginService.login("code");
		String accessToken = response.getAccessToken();
		String refreshToken = response.getRefreshToken();
		Long memberId = response.getMemberId();

		//then
		assertThat(response.getNickname()).isEqualTo("홍길동");
		assertThat(jwtProvider.getAudience(accessToken)).isEqualTo(memberId.toString());
		assertThat(jwtProvider.getAudience(refreshToken)).isEqualTo(memberId.toString());
		assertThat(response.getProfileImage()).isEqualTo("http://yyy.kakao.com/dn/img_640x640.jpg");
	}

	@Test
	@DisplayName("기존 회원 로그인을 시도했을 때, 로그인 성공")
	void auth_login_success(){
		//given
		memberRepository.save(new Member(
			"123456789",
			"홍길동",
			"sample@sample.com",
			"http://yyy.kakao.com/dn/img_640x640.jpg"
		));
		WireMock.setScenarioState("Kakao Login Success", "Started");

		//when
		LoginResponse response = loginService.login("code");
		String accessToken = response.getAccessToken();
		String refreshToken = response.getRefreshToken();
		Long memberId = response.getMemberId();

		//then
		assertThat(response.getNickname()).isEqualTo("홍길동");
		assertThat(jwtProvider.getAudience(accessToken)).isEqualTo(memberId.toString());
		assertThat(jwtProvider.getAudience(refreshToken)).isEqualTo(memberId.toString());
		assertThat(response.getProfileImage()).isEqualTo("http://yyy.kakao.com/dn/img_640x640.jpg");
	}
}
