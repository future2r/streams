package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptTakeWhileTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new JavaScriptTakeWhile<String>("take = element.length() >=2");

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] { "This", "is" }, result);
	}
}
