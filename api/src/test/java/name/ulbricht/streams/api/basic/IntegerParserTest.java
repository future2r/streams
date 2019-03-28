package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class IntegerParserTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("1", "2", "42");

		final var operation = new IntegerParser();

		final var result = operation.apply(stream).toArray(Integer[]::new);

		assertArrayEquals(new Integer[] { 1, 2, 42 }, result);
	}

}
