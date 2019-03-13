package com.ulbricht.name.streams.impl.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import name.ulbricht.streams.impl.intermediate.JavaScriptFilter;

public class JavaScriptFilterTest {

	@Test
	public void testDefault() {
		final var filter = new JavaScriptFilter<String>();

		Stream<String> stream = Stream.of("Hello", "World");
		stream = filter.apply(stream);
		assertEquals(2, stream.count());
	}

	@Test
	public void testUserScript() {
		final var filter = new JavaScriptFilter<String>();
		filter.setScript("result = element.startsWith(\"H\");");

		Stream<String> stream = Stream.of("Hello", "World");
		stream = filter.apply(stream);
		assertEquals(1, stream.count());
	}
}
