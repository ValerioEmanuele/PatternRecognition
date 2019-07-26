package blog.valerioemanuele.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.service.PointService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PointService pointService;
	
	@Test
	@DisplayName("Null point returns client error")
	@Order(1)
	void testAddNullPoint() {
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", null, null);
		assertThat(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Valid point is added")
	@Order(2)
	void testAddValidPoint() {
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", Point.builder().x(11d).y(22d).build(), null);
		assertThat(response.getStatusCode().is2xxSuccessful());
		response = testRestTemplate.postForEntity("/point", Point.builder().x(11d).y(33d).build(), null);
		assertThat(response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	@DisplayName("Same point is NOT added")
	@Order(3)
	void testSamePointNotAdded() {
		Point p = Point.builder().x(11d).y(22d).build();
		
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", p, null);
		assertThat(response.getStatusCode().is2xxSuccessful());
		
		response = testRestTemplate.postForEntity("/point", p, null);
		assertThat(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Point whit null value(s) NOT added")
	@Order(4)
	void testPointWithNullValueNotAdded() {
		Point p = new Point();
		
		ResponseEntity<Object> response = testRestTemplate.postForEntity("/point", p, null);
		assertThat(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	@DisplayName("Get All Points")
	@Order(5)
	void testGetPoints() {
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/space", Set.class);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().size()==2);
	}
	
	@Test
	@DisplayName("Get Empty Set when there aren't points")
	@Order(6)
	void testGetEmptySetWithNoPoints() {
		pointService.delete();
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/space", Set.class);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().isEmpty());
	}
	
	@Test
	@DisplayName("DELETE all points")
	@Order(7)
	void testDeleteAllPoints() {
		pointService.add(Point.builder().x(1d).y(3.d).build());
		pointService.add(Point.builder().x(31d).y(30.d).build());
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/space", Set.class);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().size()==2);
		
		testRestTemplate.delete("/space");
		response = testRestTemplate.getForEntity("/space", Set.class);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().isEmpty());
	}
	
	@Test
	@DisplayName("Get Empty Line Segment Set")
	@Order(8)
	void testGetLines() {
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/lines/{n}", Set.class, 2);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().isEmpty());
	}
	
	@Test
	@DisplayName("Get Empty Line Segment Set With only a point in the space")
	@Order(9)
	void testGetLinesWithOnePoint() {
		pointService.add(Point.builder().x(1d).y(3.d).build());
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/lines/{n}", Set.class, 2);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().isEmpty());
	}
	
	@Test
	@DisplayName("Get Line Segment Set With three points in the space")
	@Order(10)
	void testGetLinesWithTwoPoints() {
		pointService.add(Point.builder().x(1d).y(3.d).build());
		pointService.add(Point.builder().x(-13d).y(-3.d).build());
		pointService.add(Point.builder().x(-130d).y(-3.d).build());
		ResponseEntity<Set> response = testRestTemplate.getForEntity("/lines/{n}", Set.class, 3);
		assertThat(response.getStatusCode().is2xxSuccessful());
		assertThat(response.getBody().size()==3);
	}
}
