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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
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
@WebMvcTest(ContentController.class)
public class ContentControllerTest {

	private static final long CONTENT_ID = 1;
	private static final long CARD_ID = 1;
	private static final long DEFAULT_RANGE = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContentService contentService;

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
	void Content를_생성하면_생성된_Content를_반환한다() throws Exception {
	    //given
		long tmpSeq = 1;
		ContentRequestDto contentRequestDto = new ContentRequestDto("첫 번째 컨텐츠", tmpSeq, Boolean.FALSE);
		ContentResponseDto contentResponseDto = new ContentResponseDto(CONTENT_ID, DEFAULT_RANGE, "첫 번째 컨텐츠", Boolean.FALSE, CARD_ID);
		BaseResponse<ContentResponseDto> baseResponse = new BaseResponse("code", "message", contentResponseDto);
		when(contentService.create(refEq(contentRequestDto), refEq(CARD_ID))).thenReturn(contentResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(
			MockMvcRequestBuilders.post("/api/note/1/card/1/content")
				.content(objectMapper.writeValueAsString(contentRequestDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("create-content",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("info").type(JsonFieldType.STRING)
						.description("Content의 내용을 받습니다. +" + "\n"
						+ "만약 이미지라면, 이미지의 URL이 들어갑니다."),
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("Content의 생성 위치를 받습니다. +" + "\n"
							+ "만약 생성위치를 담지 않고, 요청을 보내면 가장 첫 번째 순서(=1000)로 카드가 생성됩니다."),
					fieldWithPath("isImage").type(JsonFieldType.BOOLEAN)
						.description("이미지인지 아닌지 여부를 표시합니다.")),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("Content ID"),
					fieldWithPath("data.info").type(JsonFieldType.STRING).description("Content 내용(텍스트 혹은 URL)"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("Content 생성 위치"),
					fieldWithPath("data.isImage").type(JsonFieldType.BOOLEAN).description("Content 제목"),
					fieldWithPath("data.cardId").type(JsonFieldType.NUMBER).description("Card id")
				)));


	}


}
