package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class ToStringTest {

	@Test
	public void testExecution() {
		final var stream = Stream.<Object>of("Hello", 1, 0.42);

		final var operation = new ToString<Object>();

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] { "Hello", "1", "0.42" }, result);
	}

}
