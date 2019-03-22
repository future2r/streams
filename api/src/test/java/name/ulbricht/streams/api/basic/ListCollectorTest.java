package name.ulbricht.streams.api.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class ListCollectorTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new ListCollector<String>();

		final var result = operation.apply(stream);

		assertEquals(List.of("This", "is", "a", "test"), result);
	}
}
