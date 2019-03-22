package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class NonNullFilterTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("this", "is", "a", null, "test");

		final var operation = new NonNullFilter<String>();

		assertEquals(4, operation.apply(stream).count());
	}
}
