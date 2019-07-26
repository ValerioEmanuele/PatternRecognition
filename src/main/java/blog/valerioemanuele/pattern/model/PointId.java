package blog.valerioemanuele.pattern.model;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointId implements Serializable{
	private static final long serialVersionUID = 2767104642307127681L;

	@Column
	private @NonNull Double x;
	
	@Column
	private @NonNull Double y;
}
