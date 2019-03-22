package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class DistinctTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test", "is", "a", "test");

		final var operation = new Distinct<String>();
		assertEquals(4, operation.apply(stream).count());
	}

}
