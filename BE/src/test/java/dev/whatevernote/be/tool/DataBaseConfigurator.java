package dev.whatevernote.be.tool;

import dev.whatevernote.be.repository.CardRepository;
import dev.whatevernote.be.repository.ContentRepository;
import dev.whatevernote.be.repository.NoteRepository;
import dev.whatevernote.be.service.domain.Card;
import dev.whatevernote.be.service.domain.Content;
import dev.whatevernote.be.service.domain.Note;
import dev.whatevernote.be.service.dto.request.CardRequestDto;
import dev.whatevernote.be.service.dto.request.ContentRequestDto;
import dev.whatevernote.be.service.dto.request.NoteRequestDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConfigurator implements InitializingBean {

	private static final String SET_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS = ";
	private static final String TRUNCATE_TABLE = "TRUNCATE TABLE ";
	private static final int NUMBER_OF_NOTE = 3;
	private static final int NUMBER_OF_CARD = 3;
	private static final int NUMBER_OF_CONTENT = 5;
	private static final String NOTE_TITLE = "noteTitle-";
	private static final String CARD_TITLE = "cardTitle-";
	private static final String CONTENT_STRING_INFO = "contentINFO-";
	private static final String CONTENT_IMAGE_INFO = "testimage-whatevernote-";
	private static final String CONTENT_IMAGE_EXTENSION = ".png";
	private static final int DEFAULT_RANGE = 1_000;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ContentRepository contentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tableNames;

	@Override
	public void afterPropertiesSet() throws Exception {
		entityManager.unwrap(Session.class).doWork(this::extractTableNames);
	}

	private void extractTableNames(Connection connection) throws SQLException {
		List<String> tableNames = new ArrayList<>();
		ResultSet tables = connection.getMetaData()
			.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

		try (tables) {
			while (tables.next()) {
				tableNames.add(tables.getString("table_name"));
			}

			this.tableNames = tableNames;
		}
	}

	public void clear() {
		entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
	}

	private void cleanUpDatabase(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(SET_FOREIGN_KEY_CHECKS + "0");

			for (String tableName : tableNames) {
				statement.executeUpdate(TRUNCATE_TABLE + tableName);
			}
			statement.executeUpdate(SET_FOREIGN_KEY_CHECKS + "1");
		}
	}

	public void initDataSource() {
		initNoteData();
		initCardData();
		initContentData();
	}

	private void initNoteData() {
		for (int i = 1; i <= NUMBER_OF_NOTE; i++) {
			noteRepository.save(
				Note.from(
					new NoteRequestDto(i * DEFAULT_RANGE, NOTE_TITLE + i)
				)
			);
		}
	}

	private void initCardData() {
		for (int noteId = 1; noteId <= NUMBER_OF_NOTE; noteId++) {
			for (int i = 1; i <= NUMBER_OF_CARD; i++) {
				cardRepository.save(
					Card.from(
						new CardRequestDto((long) i * DEFAULT_RANGE, CARD_TITLE + i),
						noteRepository.findById(noteId)
							.orElseThrow(() -> new IllegalArgumentException("해당 노트가 존재하지 않습니다."))
					)
				);
			}
		}
	}

	private void initContentData() {
		for (int noteId = 1; noteId <= NUMBER_OF_NOTE; noteId++) {
			for (long cardId = 1; cardId <= NUMBER_OF_CARD; cardId++) {
				for (int i = 1; i <= NUMBER_OF_CONTENT; i++) {
					contentRepository.save(
						Content.from(
							new ContentRequestDto(
								CONTENT_STRING_INFO, (long) i * DEFAULT_RANGE, Boolean.FALSE
							),
							cardRepository.findById(cardId)
								.orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."))
						)
					);
				}
			}
			for (long cardId = NUMBER_OF_CARD+1; cardId <= 2*NUMBER_OF_CARD; cardId++) {
				for (int i = 1; i <= NUMBER_OF_CONTENT; i++) {
					contentRepository.save(
						Content.from(
							new ContentRequestDto(
								CONTENT_IMAGE_INFO+i+CONTENT_IMAGE_EXTENSION, (long) i * DEFAULT_RANGE, Boolean.TRUE
							),
							cardRepository.findById(cardId)
								.orElseThrow(() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다."))
						)
					);
				}
			}
		}
	}


}
