package blog.valerioemanuele.pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import blog.valerioemanuele.pattern.model.LineSegment;
import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.provider.CollinearPointsProvider;

class TestLineSegment {

	@Test
	@DisplayName("Null points throws exceptions")
	void testNullPoints() {
		assertThrows(NullPointerException.class, () -> LineSegment.builder().points(null).build());
		assertThrows(NullPointerException.class, () -> LineSegment.builder().build());
	}
	
	@Test
	@DisplayName("Test line length with no points")
	void testLineLengthWithNoPoints() {
		SortedSet<Point> points = new TreeSet<>();
		LineSegment ls = LineSegment.builder().points(points).build();
		assertThrows(IllegalStateException.class, () -> ls.length());
	}
	
	@Test
	@DisplayName("Test line length with one point")
	void testLineLengthWithOnePoint() {
		SortedSet<Point> points = new TreeSet<>();
		points.add(Point.builder().x(1d).y(1d).build());
		LineSegment ls = LineSegment.builder().points(points).build();
		assertEquals(0, ls.length());
	}

	@ParameterizedTest
	@ArgumentsSource(CollinearPointsProvider.class)
	@DisplayName("Test line length")
	void testLineLength(List<Point> collinearPoints) {
		SortedSet<Point> points = new TreeSet<>(collinearPoints);
		LineSegment ls = LineSegment.builder().points(points).build();
		assertEquals(4, ls.length());
	}
}
