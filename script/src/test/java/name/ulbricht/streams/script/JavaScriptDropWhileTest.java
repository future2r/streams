package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptDropWhileTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new JavaScriptDropWhile<String>("drop = element.length() > 1");

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] { "a", "test" }, result);
	}
}
