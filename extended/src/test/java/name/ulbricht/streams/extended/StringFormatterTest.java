package name.ulbricht.streams.extended;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class StringFormatterTest {

	@Test
	public void testExecution() {
		final var stream = Stream.of("Hello", "World");

		final var operation = new StringFormatter<String>("Element: '%s'");

		final var result = operation.apply(stream).toArray(String[]::new);

		assertArrayEquals(new String[] { "Element: 'Hello'", "Element: 'World'" }, result);
	}

}
