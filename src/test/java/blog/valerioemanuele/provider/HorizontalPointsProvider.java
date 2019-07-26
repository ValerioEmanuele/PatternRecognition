package blog.valerioemanuele.provider;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import blog.valerioemanuele.pattern.model.Point;

public class HorizontalPointsProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		List<Point> listPoint = List.of(
				Point.builder().x(7453d).y(14118d).build(),
				Point.builder().x(2682d).y(14118d).build(),
				Point.builder().x(7821d).y(14118d).build(),
				Point.builder().x(5067d).y(14118d).build(),
				Point.builder().x(9972d).y(4652d).build(),
				Point.builder().x(16307d).y(4652d).build(),
				Point.builder().x(5766d).y(4652d).build(),
				Point.builder().x(4750d).y(4652d).build(),
				Point.builder().x(13291d).y(7996d).build(),
				Point.builder().x(20547d).y(7996d).build(),
				Point.builder().x(10411d).y(7996d).build(),
				Point.builder().x(8934d).y(7996d).build(),
				Point.builder().x(1888d).y(7657d).build(),
				Point.builder().x(7599d).y(7657d).build(),
				Point.builder().x(12772d).y(7657d).build(),
				Point.builder().x(13832d).y(7657d).build(),
				Point.builder().x(10375d).y(12711d).build(),
				Point.builder().x(14226d).y(12711d).build(),
				Point.builder().x(20385d).y(12711d).build(),
				Point.builder().x(18177d).y(12711d).build()
				);
		return Stream.of(Arguments.of(listPoint));
	}
}
