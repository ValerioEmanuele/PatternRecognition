package blog.valerioemanuele.pattern.util;

import static blog.valerioemanuele.pattern.util.Constants.NUM_OF_MIN_POINTS;

import java.text.MessageFormat;
import java.util.SortedSet;

import blog.valerioemanuele.pattern.exceptions.PointNotValidException;
import blog.valerioemanuele.pattern.model.Point;
import lombok.NonNull;

public class Validator {
	public static void validate(Point point) {
		if(point == null) {
			throw new PointNotValidException("A null was passed instead of an instance of Point class");
		}
		
		if(point.getX() == null || point.getY() == null) {
			throw new PointNotValidException("The point contains null value(s) "+point);
		}
	}

	public static void validateNumberOfMinPoints(int numOfPoints) {
		if(numOfPoints < NUM_OF_MIN_POINTS) {
			throw new IllegalArgumentException(MessageFormat.format("The minimum number of points is {0}", NUM_OF_MIN_POINTS));
		}
	}

	public static void validateLineSegment(@NonNull SortedSet<Point> points) {
		if(points == null || points.isEmpty()) {
			throw new IllegalStateException("There aren't point in the LineSegment");
		}
	}

}
