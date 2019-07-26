package blog.valerioemanuele.provider;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import blog.valerioemanuele.pattern.model.Point;


public class TwoLinesProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		List<Point> listPoint = List.of(
				Point.builder().x(4000d).y(30000d).build(),
				Point.builder().x(3500d).y(28000d).build(),
				Point.builder().x(3000d).y(26000d).build(),
				Point.builder().x(2000d).y(22000d).build(),
				Point.builder().x(1000d).y(18000d).build(),
				Point.builder().x(13000d).y(21000d).build(),
				Point.builder().x(23000d).y(16000d).build(),
				Point.builder().x(28000d).y(13500d).build(),
				Point.builder().x(28000d).y(5000d).build(),
				Point.builder().x(28000d).y(1000d).build()
				);
		return Stream.of(Arguments.of(listPoint));
	}
}
