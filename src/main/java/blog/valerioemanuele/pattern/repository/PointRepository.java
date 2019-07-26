package blog.valerioemanuele.pattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.model.PointId;

@Repository
public interface PointRepository extends JpaRepository<Point, PointId>{

	/*
	private static TreeSet<Point> inMemoryDb = new TreeSet<Point>();

	public boolean add(Point point) {
		return inMemoryDb.add(point); 
	}

	public void clear() {
		inMemoryDb.clear();
	}
	
	public int count() {
		return inMemoryDb.size();
	}

	public Set<Point> allAsSet() {
		return inMemoryDb.stream().collect(Collectors.toSet());
	}
	*/
}
