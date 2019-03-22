package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class UpperCaseTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello", "World");

		final var operation = new UpperCase();

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] {"HELLO", "WORLD"}, result);
	}

}
