package blog.valerioemanuele.pattern.api.controller;

import java.text.MessageFormat;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import blog.valerioemanuele.pattern.exceptions.PointAlreadyExistsException;
import blog.valerioemanuele.pattern.exceptions.PointNotValidException;
import blog.valerioemanuele.pattern.model.LineSegment;
import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.service.LineSegmentService;
import blog.valerioemanuele.pattern.service.PointService;

@EnableWebMvc
@RestController
public class GeometryController {
	
	@Autowired
	private PointService pointService;
	
	@Autowired
	private LineSegmentService lineSegmentService;
	
	@PostMapping(path="/point", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void add(@RequestBody Point point) {
		if(!pointService.add(point)) {
			throw new PointAlreadyExistsException(MessageFormat.format("The point {0} is already registered", point));
		}
	}
	
	@GetMapping(path="/space", produces=MediaType.APPLICATION_JSON_VALUE)
	public Set<Point> getSpace(){
		return pointService.getAll();
	}
	
	@GetMapping(path="/lines/{n}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Set<Point> lines(@PathVariable Integer n){
		return lineSegmentService.linesOfPoints(n).orElse(LineSegment.builder().points(new TreeSet<>()).build()).getPoints();
	}
	
	@DeleteMapping(path="/space")
	public void deleteSpace(){
		pointService.delete();
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private void pointNotValidHandler(PointNotValidException ex) {}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	private void pointAlreadyExistsHandler(PointAlreadyExistsException ex) {}
}
