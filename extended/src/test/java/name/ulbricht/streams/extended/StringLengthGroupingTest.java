package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class StringLengthGroupingTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new StringLengthGrouping();

		final var result = operation.apply(stream);

		assertEquals(3, result.size());

		assertEquals(1, result.get(1).size());
		assertTrue(result.get(1).contains("a"));

		assertEquals(1, result.get(2).size());
		assertTrue(result.get(2).contains("is"));

		assertEquals(2, result.get(4).size());
		assertTrue(result.get(4).contains("This"));
		assertTrue(result.get(4).contains("test"));
	}

}
