package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class RegExFilterTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello", "World");

		final var operation = new RegExFilter("^H.*");

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] {"Hello"}, result);
	}

}
