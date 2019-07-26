package blog.valerioemanuele.pattern.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(PointId.class)
public class Point implements Comparable<Point> {
	@Id
	private @NonNull Double x;
	
	@Id
	private @NonNull Double y;

	public double slopeTo(@NonNull Point that) {
		if (!this.x.equals(that.x)) {
			if (!this.y.equals(that.y)) {
				return (double) (this.y - that.y) / (this.x - that.x);
			} else {
				return 0.0;
			}
		} else if (!this.y.equals(that.y)) {
			return Double.POSITIVE_INFINITY;
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

	public int compareTo(@NonNull Point that) {
		if (this.y.doubleValue() < that.y.doubleValue()) {
			return -1;
		} else if (this.y.doubleValue() > that.y.doubleValue()) {
			return 1;
		} else if (this.x.doubleValue() < that.x.doubleValue()) {
			return -1;
		} else if (this.x.doubleValue() > that.x.doubleValue()) {
			return 1;
		} else {
			return 0;
		}
	}

    public double distanceTo(@NonNull Point that) {
    	double ac = Math.abs(that.getY() - getY());
        double cb = Math.abs(that.getX() - getX());
             
        return Math.hypot(ac, cb);
	}
}
