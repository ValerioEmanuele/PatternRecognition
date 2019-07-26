package blog.valerioemanuele.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import blog.valerioemanuele.pattern.exceptions.PointNotValidException;
import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.repository.PointRepository;
import blog.valerioemanuele.pattern.service.PointService;

@ExtendWith(MockitoExtension.class)
public class TestPointService {

	@Mock
	PointRepository pointRepository;
	
	private PointService pointService;
	
	@BeforeEach
	private void setUp() {
		pointService = new PointService(pointRepository);
	}

	@Test
	@DisplayName("Add new point")
	public void addNewPoint() {
		Point point = Point.builder().x(2d).y(3d).build();
		given(pointRepository.saveAndFlush(point)).willReturn(point);
		assertTrue(pointService.add(point));
	}

	@Test
	@DisplayName("Throws Exception when null point is passed")
	public void throwsExceptionWithNullPoint() {
		assertThrows(PointNotValidException.class, () -> pointService.add(null));
	}
	
	@Test
	@DisplayName("Throws Exception when point with null values is passed")
	public void throwsExceptionWithPointWithNullValues() {
		Point p = new Point();
		assertThrows(PointNotValidException.class, () -> pointService.add(p));
	}

	@Test
	@DisplayName("Get all points")
	public void getAllPoints() {
		Point point = Point.builder().x(2d).y(3d).build();
		Point point2 = Point.builder().x(14d).y(-11d).build();
		Point point3 = Point.builder().x(34d).y(43d).build();
		given(pointRepository.findAll()).willReturn(List.of(point, point2, point3));
		
		assertEquals(3, pointService.getAll().size());
	}

}
