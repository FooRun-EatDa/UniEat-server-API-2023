package foorun.unieat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@DisplayName("Spring Boot")
class ApiApplicationTests {
	@Autowired
	private ApplicationContext applicationContext;

	@DisplayName("context load")
	@Test
	void contextLoads() {
		if (applicationContext == null) {
			throw new RuntimeException("ApplicationContext load fail.");
		}

		String[] beans = applicationContext.getBeanDefinitionNames();

		for (String bean: beans) {
			System.out.println("BEAN: " + bean);
		}
	}
}