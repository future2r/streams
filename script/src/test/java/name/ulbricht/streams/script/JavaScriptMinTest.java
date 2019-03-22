package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptMinTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("this", "is", "a", "test");

		final var operation = new JavaScriptMin<String>();

		final var result = operation.apply(stream);

		assertTrue(result.isPresent());
		assertEquals("a", result.get());
	}
}
