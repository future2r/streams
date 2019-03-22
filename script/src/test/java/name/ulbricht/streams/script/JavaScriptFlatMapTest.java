package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptFlatMapTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello", "World");

		final var operation = new JavaScriptFlatMap<String, Integer>(
				"mappedStream = element.toString().chars().boxed()");

		final var result = operation.apply(stream).toArray(Integer[]::new);

		assertArrayEquals(new Integer[] { (int) 'H', (int) 'e', (int) 'l', (int) 'l', (int) 'o', (int) 'W', (int) 'o',
				(int) 'r', (int) 'l', (int) 'd' }, result);
	}
}
