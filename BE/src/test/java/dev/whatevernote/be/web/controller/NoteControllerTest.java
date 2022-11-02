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
import dev.whatevernote.be.repository.NoteRepository;
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

	@MockBean
	private NoteRepository noteRepository;

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
	void 단어장을_id에_따라_조회하면_해당_단어장을_반환한다() throws Exception {
	    //given
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, 1, "note-1");
		when(noteService.findById(1)).thenReturn(noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
				.get("/api/note/{NOTE_ID}", NOTE_ID)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE));


	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(noteResponseDto)))
			.andDo(document("get-one-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("seq").type(JsonFieldType.NUMBER).description("note seq"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("title")
				)));
	}

	@Test
	void 단어장을_전체_조회하면_모든_단어장이_반환된다() throws Exception {
		//given
		List<NoteResponseDto> dtos = new ArrayList<>();
		dtos.add(new NoteResponseDto(1, DEFAULT_RANGE, "note-1"));
		dtos.add(new NoteResponseDto(2, DEFAULT_RANGE*2, "note-2"));
		dtos.add(new NoteResponseDto(3, DEFAULT_RANGE*3, "note-3"));
		NoteResponseDtos noteResponseDtos = new NoteResponseDtos(dtos, false, 0);
		when(noteService.findAll(any())).thenReturn(noteResponseDtos);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/note?page=0&size=5")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(noteResponseDtos)))
			.andDo(document("get-all-notes",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("page").description("The page to retrieve"),
					parameterWithName("size").description("Entries page size")
				),
				responseFields(
					fieldWithPath("notes[].id").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("notes[].seq").type(JsonFieldType.NUMBER).description("note seq"),
					fieldWithPath("notes[].title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("has Next"),
					fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지의 넘버")
				)
			));
	}

	@Test
	void 단어장을_생성하면_생성된_단어장을_반환한다() throws Exception {
	    //given
		NoteRequestDto noteRequestDto = new NoteRequestDto(1, "첫번째 노트");
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, 1, "첫번째 노트");
		when(noteService.create(refEq(noteRequestDto))).thenReturn(noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/note")
			.content(objectMapper.writeValueAsString(noteRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isOk())
			.andDo(document("create-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("노트 생성 위치를 받습니다. +" + "\n"
							+ "만약 생성위치를 담지 않고, 요청을 보내면 가장 첫 번째 순서로 노트가 생성됩니다."),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("노트 제목을 받습니다. +" + "\n"
							+ "만약 노트 제목을 담지 않고, 요청을 보내면 빈 문자열을 제목으로 가진 노트가 생성됩니다.")
				)));
	}

	@Test
	void 단어장을_수정하면_수정된_단어장을_반환한다() throws Exception {
	    //given
		NoteRequestDto noteRequestDto = new NoteRequestDto(0, null);
		NoteResponseDto noteResponseDto = new NoteResponseDto(NOTE_ID, DEFAULT_RANGE, "단어장 제목 제목");
		when(noteService.update(any(), any())).thenReturn(noteResponseDto);

	    //when
		ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders
			.put("/api/note/{NOTE_ID}", NOTE_ID)
			.content(objectMapper.writeValueAsString(noteRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(noteResponseDto)))
			.andDo(document("update-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				),
				requestFields(
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("수정할 노트의 위치(=옮길 위치 이전의 노트 개수)를 받습니다. +" + "\n"
							+ "만약 생성위치를 담지 않고, 요청을 보내면 순서는 변하지 않습니다.")
						.optional(),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("수정할 노트의 제목을 받습니다. +" + "\n"
							+ "만약 노트 제목을 담지 않고, 요청을 보내면 제목은 변하지 않습니다.")
						.optional()),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER)
						.description("수정된 노트의 ID를 받습니다."),
					fieldWithPath("seq").type(JsonFieldType.NUMBER)
						.description("수정된 노트 위치를 받습니다."),
					fieldWithPath("title").type(JsonFieldType.STRING)
						.description("수정된 노트 제목을 받습니다."))

			)
		);
	}

	@Test
	void 단어장을_삭제하면_soft_delete_한다() throws Exception {
	    //given
		doNothing().when(noteService).delete(any());

	    //when
		ResultActions resultActions = this.mockMvc
			.perform(RestDocumentationRequestBuilders
				.delete("/api/note/{NOTE_ID}", NOTE_ID));

	    //then
		resultActions.andExpect(status().isOk())
			.andDo(document("delete-note",
				preprocessRequest(prettyPrint()),
				pathParameters(
					parameterWithName("NOTE_ID").description("note id")
				)
			));
	}

}
