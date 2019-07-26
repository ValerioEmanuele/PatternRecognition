package blog.valerioemanuele.pattern.model;

import java.util.SortedSet;

import blog.valerioemanuele.pattern.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class LineSegment {
	@NonNull
	private SortedSet<Point> points;

	public Double length() {
		Validator.validateLineSegment(points);
		if(points.size() == 1) {
			return 0d;
		}
		
		return points.first().distanceTo(points.last());
	}
}
