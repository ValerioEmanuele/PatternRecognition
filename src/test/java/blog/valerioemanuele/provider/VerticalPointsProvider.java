package blog.valerioemanuele.provider;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import blog.valerioemanuele.pattern.model.Point;

public class VerticalPointsProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		List<Point> listPoint = List.of(
				Point.builder().x(14407d).y(19953d).build(),
				Point.builder().x(14407d).y(17831d).build(),
				Point.builder().x(14407d).y(10367d).build(),
				Point.builder().x(14407d).y(17188d).build(),
				Point.builder().x(15976d).y(9945d).build(),
				Point.builder().x(15976d).y(3370d).build(),
				Point.builder().x(15976d).y(8933d).build(),
				Point.builder().x(15976d).y(4589d).build(),
				Point.builder().x(2088d).y(11500d).build(),
				Point.builder().x(2088d).y(16387d).build(),
				Point.builder().x(2088d).y(6070d).build(),
				Point.builder().x(2088d).y(7091d).build(),
				Point.builder().x(5757d).y(16647d).build(),
				Point.builder().x(5757d).y(20856d).build(),
				Point.builder().x(5757d).y(3426d).build(),
				Point.builder().x(5757d).y(13581d).build(),
				Point.builder().x(8421d).y(15144d).build(),
				Point.builder().x(8421d).y(11344d).build(),
				Point.builder().x(8421d).y(1829d).build(),
				Point.builder().x(8421d).y(18715d).build()
				);
		return Stream.of(Arguments.of(listPoint));
	}
}
