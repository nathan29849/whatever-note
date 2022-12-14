package dev.whatevernote.be.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.ContentService;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
import dev.whatevernote.be.service.dto.response.ContentResponseDto;
import dev.whatevernote.be.service.dto.response.ContentResponseDtos;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(ContentController.class)
class ContentControllerTest {

	private static final long CONTENT_ID = 1;
	private static final long CARD_ID = 1;
	private static final int NOTE_ID = 1;
	private static final long DEFAULT_RANGE = 1_000;

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
	void ????????????_card_id???_??????_????????????_??????_card???_??????_????????????_??????_????????????() throws Exception{
		//given
		List<ContentResponseDto> dtos = new ArrayList<>();
		dtos.add(new ContentResponseDto(1L, DEFAULT_RANGE, "content-1", Boolean.FALSE, CARD_ID));
		dtos.add(new ContentResponseDto(2L, DEFAULT_RANGE*2, "content-2", Boolean.FALSE, CARD_ID));
		dtos.add(new ContentResponseDto(3L, DEFAULT_RANGE*3, "content-3", Boolean.FALSE, CARD_ID));

		ContentResponseDtos contentResponseDtos = new ContentResponseDtos(dtos, false, 0);
		when(contentService.findAll(any(), any())).thenReturn(contentResponseDtos);
		BaseResponse<CardResponseDtos> baseResponse = new BaseResponse("code", "message", contentResponseDtos);


		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.get("/api/note/{NOTE_ID}/card/{CARD_ID}/content?page=0&size=5", NOTE_ID, CARD_ID)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-all-contents",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id")
				),
				requestParameters(
					parameterWithName("page").description("The page to retrieve"),
					parameterWithName("size").description("Entries page size")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.contents[].id").type(JsonFieldType.NUMBER).description("content id"),
					fieldWithPath("data.contents[].seq").type(JsonFieldType.NUMBER).description("content seq"),
					fieldWithPath("data.contents[].info").type(JsonFieldType.STRING).description("content info"),
					fieldWithPath("data.contents[].isImage").type(JsonFieldType.BOOLEAN).description("is Image"),
					fieldWithPath("data.contents[].cardId").type(JsonFieldType.NUMBER).description("card id"),
					fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("has Next"),
					fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("?????? ???????????? ?????? (0?????? ??????)")
				)
			));
	}

	@Test
	void Content???_????????????_?????????_Content???_????????????() throws Exception {
	    //given
		long tmpSeq = 1;
		ContentRequestDto contentRequestDto = new ContentRequestDto("??? ?????? ?????????", tmpSeq, Boolean.FALSE);
		ContentResponseDto contentResponseDto = new ContentResponseDto(CONTENT_ID, DEFAULT_RANGE, "??? ?????? ?????????", Boolean.FALSE, CARD_ID);
		BaseResponse<ContentResponseDto> baseResponse = new BaseResponse("code", "message", contentResponseDto);
		when(contentService.create(refEq(contentRequestDto), refEq(CARD_ID))).thenReturn(contentResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(
			RestDocumentationRequestBuilders
				.post("/api/note/{NOTE_ID}/card/{CARD_ID}/content", NOTE_ID, CARD_ID)
				.content(objectMapper.writeValueAsString(contentRequestDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("create-content",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id")
				),
				requestFields(
					fieldWithPath("info").type(JsonFieldType.STRING)
						.description("Content??? ????????? ????????????. +" + "\n"
						+ "?????? ???????????????, ???????????? URL??? ???????????????."),
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("Content??? ?????? ????????? ????????????. +" + "\n"
							+ "?????? ??????????????? ?????? ??????, ????????? ????????? ?????? ??? ?????? ??????(=1000)??? ????????? ???????????????."),
					fieldWithPath("isImage").type(JsonFieldType.BOOLEAN)
						.description("??????????????? ????????? ????????? ???????????????.")),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("Content ID"),
					fieldWithPath("data.info").type(JsonFieldType.STRING).description("Content ??????(????????? ?????? URL)"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("Content ?????? ??????"),
					fieldWithPath("data.isImage").type(JsonFieldType.BOOLEAN).description("Content ??????"),
					fieldWithPath("data.cardId").type(JsonFieldType.NUMBER).description("Card id")
				)));


	}

	@Test
	void Content???_????????????_?????????_Content???_????????????() throws Exception {
		//given
		long tmpSeq = 1;
		ContentRequestDto contentRequestDto = new ContentRequestDto("????????? ?????????", tmpSeq, Boolean.FALSE);
		ContentResponseDto contentResponseDto = new ContentResponseDto(CONTENT_ID, DEFAULT_RANGE, "????????? ?????????", Boolean.FALSE, CARD_ID);
		BaseResponse<ContentResponseDto> baseResponse = new BaseResponse("code", "message", contentResponseDto);
		when(contentService.update(any(), any(), any())).thenReturn(contentResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(
			RestDocumentationRequestBuilders
				.put("/api/note/{NOTE_ID}/card/{CARD_ID}/content/{CONTENT_ID}", NOTE_ID, CARD_ID, CONTENT_ID)
				.content(objectMapper.writeValueAsString(contentRequestDto))
				.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("update-content",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id"),
					parameterWithName("CONTENT_ID").description("content id")
				),
				requestFields(
					fieldWithPath("info").type(JsonFieldType.STRING)
						.description("????????? Content??? ????????? ????????????. +" + "\n"
							+ "?????? ???????????????, ???????????? URL??? ???????????????."),
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("????????? Content??? ?????? ????????? ????????????. +" + "\n"
							+ "?????? ??????????????? ?????? ??????, ????????? ????????? ?????? ??? ?????? ??????(=1000)??? ????????? ???????????????."),
					fieldWithPath("isImage").type(JsonFieldType.BOOLEAN)
						.description("??????????????? ????????? ????????? ???????????????.")),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("Content ID"),
					fieldWithPath("data.info").type(JsonFieldType.STRING).description("Content ??????(????????? ?????? URL)"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("Content ?????? ??????"),
					fieldWithPath("data.isImage").type(JsonFieldType.BOOLEAN).description("Content ??????"),
					fieldWithPath("data.cardId").type(JsonFieldType.NUMBER).description("Card id")
				)));


	}

	@Test
	void ?????????_????????????_soft_delete_??????() throws Exception {
		//given
		doNothing().when(contentService).delete(any());
		BaseResponse baseResponse = new BaseResponse("code", "message", null);

		//when
		ResultActions resultActions = this.mockMvc
			.perform(RestDocumentationRequestBuilders
				.delete("/api/note/{NOTE_ID}/card/{CARD_ID}/content/{CONTENT_ID}",
					NOTE_ID, CARD_ID, CONTENT_ID));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("delete-content",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id"),
					parameterWithName("CONTENT_ID").description("content id")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("null")
				)
			));
	}


}
