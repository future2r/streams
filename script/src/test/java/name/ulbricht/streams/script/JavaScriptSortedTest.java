package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptSortedTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new JavaScriptSorted<String>();

		final var result = operation.apply(stream).toArray();

		assertArrayEquals(new String[] { "This", "a", "is", "test" }, result);
	}
}
