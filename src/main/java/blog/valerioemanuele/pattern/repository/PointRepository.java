package blog.valerioemanuele.pattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.valerioemanuele.pattern.model.Point;
import blog.valerioemanuele.pattern.model.PointId;

@Repository
public interface PointRepository extends JpaRepository<Point, PointId>{

}
