package blog.valerioemanuele.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blog.valerioemanuele.pattern.model.Point;

class TestPoint {

	@Test
	@DisplayName("Test points slope")
	void testSlope() {
		Point p1 = Point.builder().x(15d).y(8d).build();
		Point p2 = Point.builder().x(10d).y(7d).build();
		Point p3 = Point.builder().x(-10d).y(-15d).build();
		
		assertEquals(0.2d, p1.slopeTo(p2));
		assertEquals(0.92d, p1.slopeTo(p3));
	}
	
	
	@Test
	@DisplayName("Test points with same X slope")
	void testPointSameXSlope() {
		Point p1 = Point.builder().x(15d).y(8d).build();
		Point p2 = Point.builder().x(15d).y(100d).build();
		
		assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2));
		assertEquals(Double.NEGATIVE_INFINITY, p2.slopeTo(p2));
	}
	
	@Test
	@DisplayName("Test points with same Y slope")
	void testPointSameYSlope() {
		Point p1 = Point.builder().x(-23d).y(100d).build();
		Point p2 = Point.builder().x(15d).y(100d).build();
		
		assertEquals(0, p1.slopeTo(p2));
	}
	
	
	@Test
	@DisplayName("Test points distance")
	void testPointDistance() {
		Point p1 = Point.builder().x(-23d).y(100d).build();
		Point p2 = Point.builder().x(15d).y(100d).build();
		
		Point p3 = Point.builder().x(1023d).y(-23.5d).build();
		
		assertEquals(1053.2655173316934, p1.distanceTo(p3));
	}
	
}
