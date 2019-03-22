package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptMapTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "Test");

		final var operation = new JavaScriptMap<String, Integer>("mapped = element.length()");

		final var result = operation.apply(stream).toArray(Integer[]::new);

		assertArrayEquals(new Integer[] { 4, 2, 1, 4 }, result);
	}
}
