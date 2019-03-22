package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class ToArrayTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new ToArray<String>();

		final var result = operation.apply(stream);

		assertArrayEquals(new String[] { "This", "is", "a", "test" }, result);
	}
}
