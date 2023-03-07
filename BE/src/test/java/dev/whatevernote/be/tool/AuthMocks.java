package dev.whatevernote.be.tool;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class AuthMocks {

	public static void setUpResponses() throws IOException {
		setUpMockTokenResponse();
		setUpMockMemberInfoResponse();
	}

	private static void setUpMockTokenResponse() throws IOException {
		stubFor(WireMock.post(urlEqualTo(
			"/oauth/token?grant_type=authorization_code&client_id=whatever-note&redirect_uri=redirectURI&code=code"))
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.withBody(getMockResponseBodyByPath("payload/get-token-response.json"))
		));
	}

	private static String getMockResponseBodyByPath(String path) throws IOException {
		return copyToString(gretMockResourceStream(path), defaultCharset());
	}

	private static InputStream gretMockResourceStream(String path) {
		return AuthMocks.class.getClassLoader().getResourceAsStream(path);
	}

	public static void setUpMockMemberInfoResponse() throws IOException {
		stubFor(WireMock.get(urlEqualTo("/v2/user/me"))
			.inScenario("Kakao Login Success")
			.whenScenarioStateIs(STARTED)
			.withHeader("Authorization", equalTo("Bearer accessToken"))
			.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.withBody(getMockResponseBodyByPath("payload/get-member-info-response.json"))
			)
		);
	}


}
