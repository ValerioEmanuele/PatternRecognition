package blog.valerioemanuele.pattern.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.repository.PointRepository;
import blog.valerioemanuele.pattern.util.Validator;

@Service
public class PointService {
	
	@Autowired
	private PointRepository pointRepository;

	public PointService(PointRepository pointRepository) {
		this.pointRepository = pointRepository;
	}

	public boolean add(Point point) {
		Validator.validate(point);
		return pointRepository.saveAndFlush(point) != null;
	}

	public void delete() {
		pointRepository.deleteAll();
	}

	public Set<Point> getAll() {
		return pointRepository.findAll().stream().collect(Collectors.toSet());
	}

}
