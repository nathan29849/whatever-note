package dev.whatevernote.be.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.NoteService;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDto;
import dev.whatevernote.be.service.dto.response.NoteResponseDtos;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(NoteController.class)
class NoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteService noteService;

	@MockBean
	private NoteRepository noteRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();


	@Test
	void 단어장을_id에_따라_조회하면_해당_단어장을_반환한다() throws Exception {
	    //given
		NoteResponseDto noteResponseDto = new NoteResponseDto(1, 1, "note-1");
		when(noteService.findById(1)).thenReturn(noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/note/1")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE));


	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(noteResponseDto)))
			.andDo(document("get-one-note",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
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
		dtos.add(new NoteResponseDto(1, 1000, "note-1"));
		dtos.add(new NoteResponseDto(2, 2000, "note-2"));
		dtos.add(new NoteResponseDto(3, 3000, "note-3"));
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
	void 단어장을_생성하면_생성된_단어장의_id를_가진_URI로_리다이렉트_된다() throws Exception {
	    //given
		NoteRequestDto noteRequestDto = new NoteRequestDto(1, "첫번째 노트");
		NoteResponseDto noteResponseDto = new NoteResponseDto(1, 1, "첫번째 노트");
		when(noteService.create(refEq(noteRequestDto))).thenReturn(noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/note")
			.content(objectMapper.writeValueAsString(noteRequestDto))
			.contentType(MediaType.APPLICATION_JSON_VALUE));

		//then
		resultActions.andExpect(status().isMovedPermanently())
			.andExpect(redirectedUrl("/api/note/1"))
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

}
