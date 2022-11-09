package dev.whatevernote.be.web.controller;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CardController.class)
public class CardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CardService cardService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void init(WebApplicationContext wc, RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wc)
			.apply(documentationConfiguration(provider))
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.alwaysDo(print())
			.build();
	}

	@Test
	void 카드를_생성하면_생성된_카드를_반환한다() throws Exception {
		//given
		CardRequestDto cardRequestDto = new CardRequestDto(1L, "첫번째 카드");
		CardResponseDto cardResponseDto = new CardResponseDto(1L, "첫번째 카드", 1L);
		when(cardService.create(refEq(cardRequestDto))).thenReturn(cardResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/note/1/card")
			.content(objectMapper.writeValueAsString(cardRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andDo(document("create-card",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("카드 생성 위치를 받습니다. +" + "\n"
							+ "만약 생성위치를 담지 않고, 요청을 보내면 가장 첫 번째 순서로 카드가 생성됩니다."),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("카드 제목을 받습니다. +" + "\n"
							+ "만약 카드 제목을 담지 않고, 요청을 보내면 빈 문자열을 제목으로 가진 카드가 생성됩니다.")),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER)
						.description("카드 ID"),
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("카드 생성 위치"),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("카드 제목"))
				));
	}

}
