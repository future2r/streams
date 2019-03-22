package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class DoubleParserTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("1", "1.5", "0.42");

		final var operation = new DoubleParser();

		final var result = operation.apply(stream).toArray(Double[]::new);

		assertArrayEquals(new Double[] { 1.0, 1.5, 0.42 }, result);
	}

}
