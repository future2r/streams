package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class LongParserTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("1", "2", "42");

		final var operation = new LongParser();

		final var result = operation.apply(stream).toArray(Long[]::new);

		assertArrayEquals(new Long[] { 1L, 2L, 42L }, result);
	}

}
