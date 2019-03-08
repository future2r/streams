package com.ulbricht.name.streams.impl.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import javax.script.ScriptException;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.impl.intermediate.JavaScriptFilter;

public class JavaScriptFilterTest {

	@Test
	public void testDefault() {
		JavaScriptFilter filter = new JavaScriptFilter();

		Stream<Object> stream = Stream.of("Hello", "World");
		stream = filter.processStream(stream);
		assertEquals(2, stream.count());
	}

	@Test
	public void testUserScript() {
		final var filter = new JavaScriptFilter();
		filter.setScript("result = element.startsWith(\"H\");");

		Stream<Object> stream = Stream.of("Hello", "World");
		stream = filter.processStream(stream);
		assertEquals(1, stream.count());
	}

	@Test
	public void testMissingResult() {
		JavaScriptFilter filter = new JavaScriptFilter();
		filter.setScript("s = \"Hello\"");

		final Stream<Object> initialStream = Stream.of("Hello", "World");
		final Stream<Object> finalStream = filter.processStream(initialStream);

		final var ex = assertThrows(RuntimeException.class, () -> finalStream.count());
		final var cause = ex.getCause();
		assertNotNull(cause);
		assertEquals(ScriptException.class, cause.getClass());
		assertEquals("Variable 'result' of type Boolean not found.", cause.getMessage());
	}
}
