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
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
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
@WebMvcTest(NoteController.class)
class NoteControllerTest {

	private static final int DEFAULT_RANGE = 1_000;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteService noteService;

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Integer NOTE_ID = 1;

	@BeforeEach
	public void init(WebApplicationContext wc, RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wc)
			.apply(documentationConfiguration(provider))
			.addFilter(new CharacterEncodingFilter("UTF-8", true))
			.alwaysDo(print())
			.build();
	}


	@Test
	void ????????????_id???_??????_????????????_??????_????????????_????????????() throws Exception {
	    //given
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, 1, "note-1");
		when(noteService.findById(1)).thenReturn(noteResponseDto);
		BaseResponse baseResponse = new BaseResponse("code", "message", noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
				.get("/api/note/{NOTE_ID}", NOTE_ID)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE));


	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-one-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("note seq"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("title")
				)));
	}

	@Test
	void ????????????_??????_????????????_??????_????????????_????????????() throws Exception {
		//given
		List<NoteResponseDto> dtos = new ArrayList<>();
		dtos.add(new NoteResponseDto(1, DEFAULT_RANGE, "note-1"));
		dtos.add(new NoteResponseDto(2, DEFAULT_RANGE*2, "note-2"));
		dtos.add(new NoteResponseDto(3, DEFAULT_RANGE*3, "note-3"));
		NoteResponseDtos noteResponseDtos = new NoteResponseDtos(dtos, false, 0);
		when(noteService.findAll(any())).thenReturn(noteResponseDtos);
		BaseResponse baseResponse = new BaseResponse("code", "message", noteResponseDtos);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/note?page=0&size=5")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("get-all-notes",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("page").description("The page to retrieve"),
					parameterWithName("size").description("Entries page size")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.notes[].id").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("data.notes[].seq").type(JsonFieldType.NUMBER).description("note seq"),
					fieldWithPath("data.notes[].title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("has Next"),
					fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("?????? ???????????? ??????")
				)
			));
	}

	@Test
	void ????????????_????????????_?????????_????????????_????????????() throws Exception {
	    //given
		NoteRequestDto noteRequestDto = new NoteRequestDto(1, "????????? ??????");
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, 1, "????????? ??????");
		when(noteService.create(refEq(noteRequestDto))).thenReturn(noteResponseDto);
		BaseResponse baseResponse = new BaseResponse("code", "message", noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/note")
			.content(objectMapper.writeValueAsString(noteRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("create-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER)
						.description("????????? ????????? ID??? ????????????."),
					fieldWithPath("data.seq").type(JsonFieldType.NUMBER)
						.description("?????? ?????? ????????? ????????????. +" + "\n"
							+ "?????? ??????????????? ?????? ??????, ????????? ????????? ?????? ??? ?????? ????????? ????????? ???????????????."),
					fieldWithPath("data.title").type(JsonFieldType.STRING)
						.description("?????? ????????? ????????????. +" + "\n"
							+ "?????? ?????? ????????? ?????? ??????, ????????? ????????? ??? ???????????? ???????????? ?????? ????????? ???????????????.")
				)));
	}

	@Test
	void ????????????_????????????_?????????_????????????_????????????() throws Exception {
	    //given
		NoteRequestDto noteRequestDto = new NoteRequestDto(0, null);
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, DEFAULT_RANGE, "????????? ?????? ??????");
		when(noteService.update(any(), any())).thenReturn(noteResponseDto);
		BaseResponse baseResponse = new BaseResponse("code", "message", noteResponseDto);


		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.put("/api/note/{NOTE_ID}", NOTE_ID)
			.content(objectMapper.writeValueAsString(noteRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("update-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("????????? ????????? ??????(=?????? ?????? ????????? ?????? ??????)??? ????????????. +" + "\n"
							+ "?????? ??????????????? ?????? ??????, ????????? ????????? ????????? ????????? ????????????.")
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
						.description("????????? ?????? ????????? ????????????."))

			)
		);
	}

	@Test
	void ????????????_????????????_soft_delete_??????() throws Exception {
	    //given
		doNothing().when(noteService).delete(any());
		BaseResponse baseResponse = new BaseResponse("code", "message", null);

	    //when
		ResultActions resultActions = this.mockMvc
			.perform(RestDocumentationRequestBuilders
				.delete("/api/note/{NOTE_ID}", NOTE_ID));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(baseResponse)))
			.andDo(document("delete-note",
				preprocessRequest(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				responseFields(
					fieldWithPath("code").type(JsonFieldType.STRING).description("response code"),
					fieldWithPath("message").type(JsonFieldType.STRING).description("response message"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("null")
			))
			);
	}

}
