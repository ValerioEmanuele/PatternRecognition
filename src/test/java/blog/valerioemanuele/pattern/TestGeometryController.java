package blog.valerioemanuele.pattern;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import blog.valerioemanuele.pattern.api.controller.GeometryController;
import blog.valerioemanuele.pattern.exceptions.PointAlreadyExistsException;
import blog.valerioemanuele.pattern.exceptions.PointNotValidException;
import blog.valerioemanuele.pattern.model.LineSegment;
import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.service.LineSegmentService;
import blog.valerioemanuele.pattern.service.PointService;
import blog.valerioemanuele.provider.CollinearPointsProvider;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = GeometryController.class)
@AutoConfigureMockMvc
class TestGeometryController {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PointService pointService;

	@MockBean
	private LineSegmentService lineSegmentService;
	
	@Test
	@DisplayName("A null point cause BadRequest Response")
	void add_shouldReturnBadRequestWithNullInput() throws Exception {
		given(pointService.add(null)).willThrow(PointNotValidException.class);

		mvc.perform(post("/point")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(null)))
						.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("An empty point cause BadRequest Response")
	void add_shouldReturnBadRequestWithEmptyJson() throws Exception {
		given(pointService.add(new Point())).willThrow(PointNotValidException.class);
		String jsonValue = asJsonString(new EmptyJson());
		
		mvc.perform(post("/point")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue))
						.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("An point with null coordinates cause BadRequest Response")
	void add_shouldReturnBadRequestWithInvalidPoint() throws Exception {
		Point p = new Point();
		given(pointService.add(p)).willThrow(PointNotValidException.class);

		String jsonValue = asJsonString(p);
		
		mvc.perform(post("/point")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonValue))
						.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("A point is not added correctly")
	void add_shouldAddPoint() throws Exception {
		Point p = Point.builder().x(1.5).y(5.3).build();

		given(pointService.add(p)).willReturn(true);

		mvc.perform(post("/point")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(p)))
						.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("A point is not added because already exists")
	void add_shouldNotAddPoint() throws Exception {
		Point p = Point.builder().x(1.5).y(5.3).build();

		given(pointService.add(p)).willThrow(PointAlreadyExistsException.class);

		mvc.perform(post("/point")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(p)))
						.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("All point are returned correctly")
	void space_shouldReturnAllPoints() throws Exception {
		Set<Point> set = Set.of(Point.builder().x(1d).y(2.d).build(),Point.builder().x(5d).y(6d).build());
		given(pointService.getAll()).willReturn(set);

		mvc.perform(get("/space")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(content().json(asJsonString(set)));
	}
	
	@Test
	@DisplayName("Empty collection is returned where there aren't points")
	void space_shouldReturnEmptyCollections() throws Exception {
		Set<Point> set = Set.of();
		given(pointService.getAll()).willReturn(set);

		mvc.perform(get("/space")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(content().json(asJsonString(set)));
	}
	
	@ParameterizedTest
	@ArgumentsSource(CollinearPointsProvider.class)
	@DisplayName("Get Longest Line")
	void lines_shouldReturnLongestLine(List<Point> collinearPoints) throws Exception {
		SortedSet<Point> points = new TreeSet<>(collinearPoints);
		LineSegment ls = LineSegment.builder().points(points).build();
		given(lineSegmentService.linesOfPoints(3)).willReturn(Optional.of(ls));

		mvc.perform(get("/lines/{n}", 3)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
						.andExpect(content().json(asJsonString(ls.getPoints())));
	}
	
	@Test
	@DisplayName("Empty is passed instead of number of points")
	void lines_shouldReturnNotFound() throws Exception {
		
		mvc.perform(get("/lines/{n}", "")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Not valid number is passed")
	void lines_shouldReturnBadRequest() throws Exception {
		
		mvc.perform(get("/lines/{n}", "11abc")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Delete works correctly")
	void delete_shouldDeleteAllPoints() throws Exception {
		
		mvc.perform(delete("/space")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@JsonSerialize
	public class EmptyJson { }

}
