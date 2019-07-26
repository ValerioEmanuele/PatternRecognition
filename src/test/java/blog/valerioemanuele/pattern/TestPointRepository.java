package blog.valerioemanuele.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.model.PointId;
import blog.valerioemanuele.pattern.repository.PointRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestPointRepository {

	@Autowired
	private PointRepository pointRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@BeforeEach
	private void before() {
		pointRepository.deleteAll();
	}

	@Test
	@DisplayName("New Point is inserted correctly")
	void addNewPoint() {
		Point point = Point.builder().x(2d).y(3d).build();
		Point savedPoint = entityManager.persistAndFlush(point);
		assertNotNull(pointRepository.findById(PointId.builder().x(savedPoint.getX()).y(savedPoint.getY()).build()));
	}
	
	@Test
	@DisplayName("Same Point cannot be added twice")
	void samePointNotAdded() {
		Point point = Point.builder().x(2d).y(3d).build();
		assertNotNull(entityManager.persistAndFlush(point));
		assertNotNull(entityManager.persistAndFlush(point));
		
		List<PointId> id = List.of(PointId.builder().x(point.getX()).y(point.getY()).build());
		assertEquals(1, pointRepository.findAllById(id).size());
	}
	
	@Test
	@DisplayName("Retrieve all points")
	void retrieveAllPoints() {
		Point point = Point.builder().x(2d).y(3d).build();
		Point point2 = Point.builder().x(6d).y(13d).build();
		Point point3 = Point.builder().x(10d).y(-12d).build();
		
		entityManager.persistAndFlush(point);
		entityManager.persistAndFlush(point2);
		entityManager.persistAndFlush(point3);
		
		Set<Point> allPoint = pointRepository.findAll().stream().collect(Collectors.toSet());
		assertEquals(3, allPoint.size());
	}
	
	@Test
	@DisplayName("Remove all points")
	void removeAllPoints() {
		Point point = Point.builder().x(2d).y(3d).build();
		Point point2 = Point.builder().x(6d).y(13d).build();
		
		entityManager.persistAndFlush(point);
		entityManager.persistAndFlush(point2);
		
		Set<Point> allPoints = pointRepository.findAll().stream().collect(Collectors.toSet());
		assertEquals(2, allPoints.size());
		
		pointRepository.deleteAll();
		allPoints = pointRepository.findAll().stream().collect(Collectors.toSet());
		assertTrue(allPoints.isEmpty());
	}

}
