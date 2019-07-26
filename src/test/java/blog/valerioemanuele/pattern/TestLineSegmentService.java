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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.repository.PointRepository;
import blog.valerioemanuele.pattern.service.LineSegmentService;
import blog.valerioemanuele.pattern.service.PointService;
import blog.valerioemanuele.provider.CollinearPointsProvider;
import blog.valerioemanuele.provider.HorizontalPointsProvider;
import blog.valerioemanuele.provider.RandomPointsProvider;
import blog.valerioemanuele.provider.TwoLinesProvider;
import blog.valerioemanuele.provider.VerticalPointsProvider;

@ExtendWith(MockitoExtension.class)
class TestLineSegmentService {
	@Mock
	PointRepository pointRepository;
	
	private LineSegmentService lineSegmentService;
	private PointService pointService;
	
	@BeforeEach
	private void setUp() {
		lineSegmentService = new LineSegmentService(pointRepository);
		pointService = new PointService(pointRepository);
	}
	
	@Test
	@DisplayName("Throws exceptions if the number of points is less than 2")
	void testLineSegmentPassingThroughInvalidNPoints() {
		assertThrows(IllegalArgumentException.class, () -> lineSegmentService.linesOfPoints(1));
		assertThrows(IllegalArgumentException.class, () -> lineSegmentService.linesOfPoints(0));
		assertThrows(IllegalArgumentException.class, () -> lineSegmentService.linesOfPoints(-1));
	}
	
	@Test
	@DisplayName("Returns empty if there aren't points or there is only one point")
	void testLineSegmentReturnsEmpty() {
		assertTrue(lineSegmentService.linesOfPoints(2).isEmpty());
		
		pointService.add(Point.builder().x(1d).y(1d).build());
		assertTrue(lineSegmentService.linesOfPoints(2).isEmpty());
	}
	
	@Test
	@DisplayName("Length of line segment with only two points")
	void testLineSegmentReturnsOneLine() {
		given(pointRepository.findAll()).willReturn(List.of(Point.builder().x(10d).y(1d).build(), Point.builder().x(1d).y(100d).build()));
		assertEquals(99.40824915468535, lineSegmentService.linesOfPoints(2).get().length());
	}
	
	@ParameterizedTest
	@ArgumentsSource(CollinearPointsProvider.class)
	@DisplayName("Returns the length of one line segment with three points")
	void testLineSegmentWithThreePoints(List<Point> points) {
		given(pointRepository.findAll()).willReturn(points);
		assertEquals(4, lineSegmentService.linesOfPoints(3).get().length());
	}
	
	@ParameterizedTest
	@ArgumentsSource(TwoLinesProvider.class)
	@DisplayName("Returns the longest line between two line length")
	void testLineLengthWithTwoLines(List<Point> points) {
		given(pointRepository.findAll()).willReturn(points);
		
		assertEquals(27950.84971874737, lineSegmentService.linesOfPoints(4).get().length());	
	}
	
	@ParameterizedTest
	@ArgumentsSource(RandomPointsProvider.class)
	@DisplayName("Returns empty set")
	void testLineLengthWithRandomPoints(List<Point> points) {
		points.stream().forEach(pointService::add);
		
		assertTrue(lineSegmentService.linesOfPoints(3).isEmpty());
	}
	
	@ParameterizedTest
	@ArgumentsSource(TwoLinesProvider.class)
	@DisplayName("Returns two line length")
	void testLineLengthWithVerticalLines(List<Point> points) {
		given(pointRepository.findAll()).willReturn(points);
		assertEquals(27950.84971874737, lineSegmentService.linesOfPoints(4).get().length());	
	}
	
	@ParameterizedTest
	@ArgumentsSource(VerticalPointsProvider.class)
	@DisplayName("Returns the longest line between five vertical lines")
	void testLineLengthOfFiveVerticalLines(List<Point> points) {
		given(pointRepository.findAll()).willReturn(points);
		
		assertEquals(17430, lineSegmentService.linesOfPoints(4).get().length());	
	}
	
	@ParameterizedTest
	@ArgumentsSource(HorizontalPointsProvider.class)
	@DisplayName("Returns the longest line between five horizontal lines")
	void testLineLengthOfFiveHorizontalLines(List<Point> points) {
		given(pointRepository.findAll()).willReturn(points);
		
		assertEquals(11944, lineSegmentService.linesOfPoints(4).get().length());	
	}
}
