package blog.valerioemanuele.pattern.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import blog.valerioemanuele.pattern.model.LineSegment;
import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.repository.PointRepository;
import blog.valerioemanuele.pattern.util.Constants;
import blog.valerioemanuele.pattern.util.Validator;

@Service
public class LineSegmentService {
	
	private PointService pointService;

	public LineSegmentService(PointRepository pointRepository) {
		pointService = new PointService(pointRepository);
	}

	public Optional<LineSegment> linesOfPoints(int numOfPoints) {
		Validator.validateNumberOfMinPoints(numOfPoints);
		Set<Point> allPoints = pointService.getAll();
		
		if (allPoints.size() < Constants.NUM_OF_MIN_POINTS) {
			return Optional.empty();
		}
		
		//it collects the lines that are made up of numOfPoints or more
		Set<LineSegment> lines = new HashSet<LineSegment>();
		allPoints.forEach(q -> {
			collinearPoints(allPoints, q).entrySet().stream()
					.filter(map -> map.getValue().size() >= numOfPoints)
					.forEach(item -> lines.add(LineSegment.builder().points(item.getValue()).build()));
		});
		
		//returns the longest line
		return lines.stream().max(Comparator.comparing(LineSegment::length));
	}

	/**
	 * It collect allPoints passed as parameters with the slope they make with Point q
	 * and put into a Map
	 * @param allPoints - all the points added to the set
	 * @param q - the point to calculate the slope
	 * @return A map where the key is the slope and the value are all the collinear points 
	 */
	private Map<Double, SortedSet<Point>> collinearPoints(Set<Point> allPoints, Point q) {
		Map<Double, SortedSet<Point>> pointsCollectedBySlope = new HashMap<>();
		allPoints.stream().filter(p1 -> !q.equals(p1)).forEach(p -> {
			double slope = q.slopeTo(p);
			pointsCollectedBySlope.put(slope, pointsCollectedBySlope.get(slope) == null ? 
				Stream.of(p, q).collect(Collectors.toCollection(TreeSet::new))
				: 
				Stream.concat(pointsCollectedBySlope.get(slope).stream(), Stream.of(p)).collect(Collectors.toCollection(TreeSet::new))
			);
		});
		return pointsCollectedBySlope;
	}

}
