package dev.whatevernote.be.service;

import dev.whatevernote.be.tool.DataBaseConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class InitIntegrationTest {

	@Autowired
	private DataBaseConfigurator testData;

	@BeforeEach
	void setUpDataBase() {
		testData.clear();
		testData.initDataSource();
	}
}
