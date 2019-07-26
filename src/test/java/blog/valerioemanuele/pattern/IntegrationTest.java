package blog.valerioemanuele.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import blog.valerioemanuele.pattern.model.Point;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	@DisplayName("Null point returns client error")
	void testAddNullPoint() {
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", null, null);
		assertThat(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Valid point is added")
	void testAddValidPoint() {
		Point p = Point.builder().x(11d).y(22d).build();
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", p, null);
		assertThat(response.getStatusCode().is2xxSuccessful());
	}

}
