package dev.whatevernote.be.service;

import dev.whatevernote.be.tool.DataBaseConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class InitIntegrationTest {

	protected static final int DEFAULT_RANGE = 1_000;
	protected static final String NOT_FOUND_ID = "존재하지 않는 ID 입니다.";

	@Autowired
	private DataBaseConfigurator testData;

	@BeforeEach
	void setUpDataBase() {
		testData.clear();
		testData.initDataSource();
	}
}
