package blog.valerioemanuele.provider;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import blog.valerioemanuele.pattern.model.Point;

public class CollinearPointsProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		List<Point> listPoint = List.of(
				Point.builder().x(1d).y(1d).build(),
				Point.builder().x(4d).y(1d).build(),
				Point.builder().x(5d).y(1d).build()
				);
		return Stream.of(Arguments.of(listPoint));
	}
}
