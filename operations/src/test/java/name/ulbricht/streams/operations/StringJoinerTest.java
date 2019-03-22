package name.ulbricht.streams.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class StringJoinerTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello", "World");

		final var operation = new StringJoiner("--", ">>", "<<");

		assertEquals(">>Hello--World<<", operation.apply(stream));
	}

}
