package blog.valerioemanuele.provider;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import blog.valerioemanuele.pattern.model.Point;

public class RandomPointsProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		List<Point> listPoint = List.of(
				Point.builder().x(19787d).y(4751d).build(),
				Point.builder().x(104d).y(25409d).build(),
				Point.builder().x(31481d).y(21752d).build(),
				Point.builder().x(20373d).y(18488d).build(),
				Point.builder().x(17851d).y(21183d).build(),
				Point.builder().x(23366d).y(14132d).build(),
				Point.builder().x(23137d).y(27386d).build(),
				Point.builder().x(31540d).y(18857d).build(),
				Point.builder().x(31793d).y(14683d).build(),
				Point.builder().x(22097d).y(26229d).build(),
				Point.builder().x(3815d).y(413d).build(),
				Point.builder().x(5184d).y(4294d).build(),
				Point.builder().x(11218d).y(28945d).build(),
				Point.builder().x(12752d).y(16555d).build(),
				Point.builder().x(9538d).y(30203d).build(),
				Point.builder().x(12007d).y(13977d).build(),
				Point.builder().x(8878d).y(22654d).build(),
				Point.builder().x(25908d).y(18733d).build(),
				Point.builder().x(2832d).y(4356d).build(),
				Point.builder().x(29773d).y(24237d).build(),
				Point.builder().x(16923d).y(3119d).build(),
				Point.builder().x(20698d).y(6625d).build(),
				Point.builder().x(20486d).y(9339d).build(),
				Point.builder().x(10672d).y(15354d).build(),
				Point.builder().x(25306d).y(5579d).build(),
				Point.builder().x(32440d).y(23390d).build(),
				Point.builder().x(13105d).y(3941d).build(),
				Point.builder().x(29382d).y(20825d).build(),
				Point.builder().x(13720d).y(19665d).build(),
				Point.builder().x(4740d).y(9930d).build(),
				Point.builder().x(9419d).y(17994d).build(),
				Point.builder().x(32713d).y(20985d).build(),
				Point.builder().x(22579d).y(5513d).build(),
				Point.builder().x(10697d).y(17926d).build(),
				Point.builder().x(1311d).y(7134d).build(),
				Point.builder().x(30644d).y(9811d).build(),
				Point.builder().x(30817d).y(19081d).build(),
				Point.builder().x(3181d).y(26256d).build()				
				);
		return Stream.of(Arguments.of(listPoint));
	}

}
