package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptAnyMatchTest {

	@Test
	public void testAllMatch_true() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new JavaScriptAnyMatch<String>("matches = element.length() == 2");

		assertTrue(operation.apply(stream).booleanValue());
	}

	@Test
	public void testAllMatch_False() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var operation = new JavaScriptAnyMatch<String>("matches = element.length() == 5");

		assertFalse(operation.apply(stream).booleanValue());
	}

}
