package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class SortedReverseTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new SortedReverse<String>();

		final var result = operation.apply(stream).toArray();

		assertArrayEquals(new String[] { "test", "is", "a", "This" }, result);
	}
}
