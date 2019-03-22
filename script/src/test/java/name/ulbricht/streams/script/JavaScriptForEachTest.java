package name.ulbricht.streams.script;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public final class JavaScriptForEachTest {

	private final class TestPrintStream extends PrintStream {

		final StringBuilder buffer = new StringBuilder();

		TestPrintStream(final PrintStream out) {
			super(out);
		}

		@Override
		public void print(final String s) {
			super.print(s);
			buffer.append(s);
		}
	}

	@Test
	public void testExecution() {
		final var stream = Stream.of("This", "is", "a", "test");

		final var tempOut = System.out;
		try {
			final var out = new TestPrintStream(System.out);
			System.setOut(out);

			final var operation = new JavaScriptForEach<String>("java.lang.System.out.println(element);");
			operation.apply(stream);
			assertEquals("Thisisatest", out.buffer.toString());
		} finally {
			System.setOut(tempOut);
		}
	}
}
