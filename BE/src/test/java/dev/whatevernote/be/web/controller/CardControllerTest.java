package dev.whatevernote.be.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.common.BaseResponse;
import dev.whatevernote.be.service.CardService;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
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

	private static final long DEFAULT_RANGE = 1_000L;
	private static final int NOTE_ID = 1;

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
		Long expectedCardId = 1L;
		NoteRequestDto noteRequestDto = new NoteRequestDto(NOTE_ID, "첫번째 노트");
		Note note = Note.from(noteRequestDto);
		CardRequestDto cardRequestDto = new CardRequestDto(expectedCardId, "첫번째 카드");
		Card card = Card.from(cardRequestDto, note);


		CardResponseDto cardResponseDto = new CardResponseDto(expectedCardId, "첫번째 카드", DEFAULT_RANGE, NOTE_ID);
		when(cardService.create(refEq(cardRequestDto), refEq(NOTE_ID))).thenReturn(cardResponseDto);
		BaseResponse<CardResponseDtos> baseResponse = new BaseResponse("code", "message", cardResponseDto);


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
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("카드 ID"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("카드 생성 위치"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("카드 제목"))
				));
	}

	@Test
	void 단어장_id에_해당하는_카드들을_전체_조회하면_해당_단어장의_모든_카드들이_반환된다() throws Exception {
		//given
		List<CardResponseDto> dtos = new ArrayList<>();
		dtos.add(new CardResponseDto(1L, "card-1", DEFAULT_RANGE, NOTE_ID));
		dtos.add(new CardResponseDto(2L, "card-2", DEFAULT_RANGE*2, NOTE_ID));
		dtos.add(new CardResponseDto(3L, "card-3", DEFAULT_RANGE*3, NOTE_ID));

		CardResponseDtos cardResponseDtos = new CardResponseDtos(dtos, false, 0);
		when(cardService.findAll(any(), any())).thenReturn(cardResponseDtos);
		BaseResponse<CardResponseDtos> baseResponse = new BaseResponse("code", "message", cardResponseDtos);


		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/note/1/card?page=0&size=5")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-all-cards",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
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
					fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("has Next"),
					fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지의 넘버")
				)
			));
	}

}
