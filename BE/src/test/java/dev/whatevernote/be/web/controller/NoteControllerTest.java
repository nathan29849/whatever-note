package dev.whatevernote.be.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.whatevernote.be.hello.web.controller.NoteController;
import dev.whatevernote.be.hello.service.NoteService;
import dev.whatevernote.be.hello.service.dto.response.NoteResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
@WebMvcTest(NoteController.class)
class NoteControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@MockBean
	private NoteService noteService;
	
	ObjectMapper objectMapper = new ObjectMapper();


	@Test
	void 단어장을_id에_따라_조회하면_해당_단어장을_반환한다() throws Exception {
	    //given
		NoteResponseDto noteResponseDto = new NoteResponseDto(1L, 1, "note-1");
		when(noteService.findById(1L)).thenReturn(noteResponseDto);

		//when
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/note/1")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE));


	    //then
		resultActions.andExpect(status().isOk())
			.andExpect(content().string(objectMapper.writeValueAsString(noteResponseDto)))
			.andDo(document("get-all-notes",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("note id"),
					fieldWithPath("order").type(JsonFieldType.NUMBER).description("note order"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("title")
				)));
	}

}