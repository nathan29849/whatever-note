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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.CardDetailResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDto;
import dev.whatevernote.be.service.dto.response.CardResponseDtos;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CardController.class)
class CardControllerTest {

	private static final long DEFAULT_RANGE = 1_000L;
	private static final int NOTE_ID = 1;
	private static final long CARD_ID = 1;
	private static final String TEMP_IMAGE_URL = "https://en.wikipedia.org/wiki/Image#/media/File:Image_created_with_a_mobile_phone.png";

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
	void ?????????_????????????_?????????_?????????_????????????() throws Exception {
		//given
		Long expectedCardId = 1L;
		CardRequestDto cardRequestDto = new CardRequestDto(expectedCardId, "????????? ??????");

		CardResponseDto cardResponseDto = new CardResponseDto(expectedCardId, "????????? ??????", DEFAULT_RANGE, NOTE_ID);
		when(cardService.create(refEq(cardRequestDto), refEq(NOTE_ID))).thenReturn(cardResponseDto);
		BaseResponse<CardResponseDto> baseResponse = new BaseResponse("code", "message", cardResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.post("/api/note/{NOTE_ID}/card", NOTE_ID)
			.content(objectMapper.writeValueAsString(cardRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("create-card",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("?????? ?????? ????????? ????????????. +" + "\n"
							+ "?????? ??????????????? ?????? ??????, ????????? ????????? ?????? ??? ?????? ????????? ????????? ???????????????."),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("?????? ????????? ????????????. +" + "\n"
							+ "?????? ?????? ????????? ?????? ??????, ????????? ????????? ??? ???????????? ???????????? ?????? ????????? ???????????????.")),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????? ID"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("?????? ??????"),
					fieldWithPath("data.noteId").type(JsonFieldType.NUMBER).description("note id")
				)));
	}

	@Test
	void ?????????id???_??????id???_????????????_??????_?????????_????????????() throws Exception {
		//given
		NoteRequestDto noteRequestDto = new NoteRequestDto(NOTE_ID, "????????? ??????");
		Note note = Note.from(noteRequestDto);
		CardRequestDto cardRequestDto = new CardRequestDto(CARD_ID, "????????? ??????");
		Card card = Card.from(cardRequestDto, note);
		ContentRequestDto contentRequestDto1 = new ContentRequestDto("??? ?????? ?????????", DEFAULT_RANGE, Boolean.FALSE);
		ContentRequestDto contentRequestDto2 = new ContentRequestDto(TEMP_IMAGE_URL, DEFAULT_RANGE, Boolean.TRUE);
		List<Content> contents = new ArrayList<>();
		contents.add(Content.from(contentRequestDto1, card));
		contents.add(Content.from(contentRequestDto2, card));

		CardDetailResponseDto cardDetailResponseDto = CardDetailResponseDto.from(card, NOTE_ID, contents);
		when(cardService.findById(NOTE_ID, CARD_ID)).thenReturn(cardDetailResponseDto);
		BaseResponse<CardDetailResponseDto> baseResponse = new BaseResponse("code", "message", cardDetailResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.get("/api/note/{NOTE_ID}/card/{CARD_ID}", NOTE_ID, CARD_ID)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));


		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-one-card",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.cardId").type(JsonFieldType.NULL).description("card id"),
					fieldWithPath("data.cardSeq").type(JsonFieldType.NUMBER).description("card seq"),
					fieldWithPath("data.cardTitle").type(JsonFieldType.STRING).description("card title"),
					fieldWithPath("data.noteId").type(JsonFieldType.NULL).description("note id"),
					fieldWithPath("data.contents[].id").type(JsonFieldType.NULL).description("content id"),
					fieldWithPath("data.contents[].seq").type(JsonFieldType.NUMBER).description("content seq"),
					fieldWithPath("data.contents[].info").type(JsonFieldType.STRING).description("content info"),
					fieldWithPath("data.contents[].isImage").type(JsonFieldType.BOOLEAN).description("content isImage"),
					fieldWithPath("data.contents[].cardId").type(JsonFieldType.NULL).description("card id")
				)));
	}

	@Test
	void ?????????_id???_????????????_????????????_??????_????????????_??????_????????????_??????_????????????_????????????() throws Exception {
		//given
		List<CardResponseDto> dtos = new ArrayList<>();
		dtos.add(new CardResponseDto(1L, "card-1", DEFAULT_RANGE, NOTE_ID));
		dtos.add(new CardResponseDto(2L, "card-2", DEFAULT_RANGE*2, NOTE_ID));
		dtos.add(new CardResponseDto(3L, "card-3", DEFAULT_RANGE*3, NOTE_ID));

		CardResponseDtos cardResponseDtos = new CardResponseDtos(dtos, false, 0);
		when(cardService.findAll(any(), any())).thenReturn(cardResponseDtos);
		BaseResponse<CardResponseDtos> baseResponse = new BaseResponse("code", "message", cardResponseDtos);


		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.get("/api/note/{NOTE_ID}/card?page=0&size=5", NOTE_ID)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-all-cards",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				requestParameters(
					parameterWithName("page").description("The page to retrieve"),
					parameterWithName("size").description("Entries page size")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.cards[].id").type(JsonFieldType.NUMBER).description("card id"),
					fieldWithPath("data.cards[].seq").type(JsonFieldType.NUMBER).description("card seq"),
					fieldWithPath("data.cards[].title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("data.cards[].noteId").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("has Next"),
					fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("?????? ???????????? ??????")
				)
			));
	}

	@Test
	void ?????????_????????????_?????????_?????????_????????????() throws Exception {
	    //given
		CardRequestDto cardRequestDto = new CardRequestDto(0L, null);
		CardResponseDto cardResponseDto = new CardResponseDto(CARD_ID, "????????? ??????", DEFAULT_RANGE, 1);
		when(cardService.update(any(), any(), any())).thenReturn(cardResponseDto);
		BaseResponse baseResponse = new BaseResponse("code", "message", cardResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.put("/api/note/{NOTE_ID}/card/{CARD_ID}", NOTE_ID, CARD_ID)
			.content(objectMapper.writeValueAsString(cardRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("update-card",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id")
				),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("????????? ????????? ??????(=?????? ?????? ????????? ?????? ??????)??? ????????????. +" + "\n"
							+ "?????? ???????????? ?????? ??????, ????????? ????????? ????????? ????????? ????????????.")
						.optional(),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("????????? ????????? ????????? ????????????. +" + "\n"
							+ "?????? ?????? ????????? ?????? ??????, ????????? ????????? ????????? ????????? ????????????.")
						.optional()),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER)
						.description("????????? ????????? ID??? ????????????."),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER)
						.description("????????? ?????? ????????? ????????????."),
					fieldWithPath("data.title").type(JsonFieldType.STRING)
						.description("????????? ?????? ????????? ????????????."),
					fieldWithPath("data.noteId").type(JsonFieldType.NUMBER)
						.description("????????? ????????? ?????? ??????????????????.")
					)
				)
			);
	}

	@Test
	void ?????????_????????????_soft_delete_??????() throws Exception {
	    //given
		doNothing().when(cardService).delete(any());
		BaseResponse baseResponse = new BaseResponse("code", "message", null);

		//when
		ResultActions resultActions = this.mockMvc
			.perform(RestDocumentationRequestBuilders
				.delete("/api/note/{NOTE_ID}/card/{CARD_ID}", NOTE_ID, CARD_ID));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("delete-card",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id"),
					parameterWithName("CARD_ID").description("card id")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("null")
				)
			));
	}

}
